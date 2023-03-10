package com.hackathon.event.service.impl;

import com.hackathon.event.dto.ProgressFlowRequestDto;
import com.hackathon.event.model.*;
import com.hackathon.event.model.enumeration.StatusType;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.repository.FlowRepository;
import com.hackathon.event.repository.ParticipantRepository;
import com.hackathon.event.repository.ProgressRepository;
import com.hackathon.event.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final ProgressRepository progressRepository;
    private final FlowRepository flowRepository;

    public void saveProgress(Long eventId,  Long participantId,
                      Integer week_no, ProgressFlowRequestDto progressFlowRequestDto){
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        Participant participant = participantRepository.findById(participantId).orElseThrow
                (() -> new EntityNotFoundException("Registration not found"));

        List<Progress> progressToSave= new ArrayList<>();
        Flow flow = new Flow();

        if(participant.getProgress().isEmpty()){
            Progress progress = new Progress();
            progress.setWeek(week_no);
            StatusType statusType = StatusType.valueOf(progressFlowRequestDto.getStatus());
            flow.setStatus(statusType);
            flow.setComment(progressFlowRequestDto.getComment());
            progress.setFlow(new ArrayList<>());
            progress.getFlow().add(flow);
            flow.setProgress(progress);
            flowRepository.save(flow);
            progress.setParticipant(participant);
            progressRepository.save(progress);
            progressToSave.add(progress);
            participant.setProgress(progressToSave);
            participantRepository.save(participant);
        }else{
            for(Progress progress: participant.getProgress()){
                if(week_no.equals(progress.getWeek())){
                    flow.setProgress(progress);
                    StatusType statusType = StatusType.valueOf(progressFlowRequestDto.getStatus());
                    flow.setStatus(statusType);
                    flow.setComment(progressFlowRequestDto.getComment());
                    flowRepository.save(flow);
                    progress.getFlow().add(flow);
                    progressToSave.add(progress);

                    progressRepository.save(progress);
                    participant.setProgress(progressToSave);
                    participantRepository.save(participant);
                }
            }
            for(Progress progress: participant.getProgress()){
                if(!week_no.equals(progress.getWeek())){
                    flow.setProgress(progress);
                    progress.setWeek(week_no);
                    StatusType statusType = StatusType.valueOf(progressFlowRequestDto.getStatus());
                    flow.setStatus(statusType);
                    flow.setComment(progressFlowRequestDto.getComment());
                    flowRepository.save(flow);

                    progress.getFlow().add(flow);
                    progressToSave.add(progress);
                    progressRepository.save(progress);

                    participant.setProgress(progressToSave);
                    participantRepository.save(participant);
                }
            }
        }
    }
}
