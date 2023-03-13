package com.hackathon.event.service.impl;

import com.hackathon.event.dto.FlowRequestDto;
import com.hackathon.event.model.*;
import com.hackathon.event.model.enumeration.StatusType;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.repository.FlowRepository;
import com.hackathon.event.repository.ParticipantRepository;
import com.hackathon.event.repository.ProgressRepository;
import com.hackathon.event.service.ParticipantService;
import com.zaxxer.hikari.util.FastList;
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
                      Integer week_no, FlowRequestDto flowRequestDto){
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        Participant participant = participantRepository.findById(participantId).orElseThrow
                (() -> new EntityNotFoundException("Registration not found"));

        List<Progress> progresses = participant.getProgress();

        Flow flow = new Flow();
        flow.setStatus(StatusType.valueOf(flowRequestDto.getStatus()));
        flow.setComment(flowRequestDto.getComment());

        Progress progress = new Progress();

        if(progresses.isEmpty()) {
            progress.setParticipant(participant);
            progress.setWeek(week_no);
            progress.setFlow(new ArrayList<>());
            flow.setProgress(progress);
            progress.getFlow().add(flow);
        }
        else {
            progress = getProgressByWeekNumber(week_no);
            flow.setProgress(progress);
            progress.setParticipant(participant);
            progress.getFlow().add(flow);
        }
        participantRepository.save(participant);
        progressRepository.save(progress);
        flowRepository.save(flow);
    }

    public Progress getProgressByWeekNumber(Integer week_no) {
        List<Progress> allProgresses = progressRepository.findAll();
        for(Progress progress : allProgresses) {
            if(progress.getWeek().equals(week_no)) {
                return progress;
            }
        }
        Progress progress = new Progress();
        progress.setWeek(week_no);
        progress.setFlow(new ArrayList<>());

        return progress;
    }
}
