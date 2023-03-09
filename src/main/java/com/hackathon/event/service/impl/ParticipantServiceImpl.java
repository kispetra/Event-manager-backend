package com.hackathon.event.service.impl;

import com.hackathon.event.dto.ProgressFlowRequestDto;
import com.hackathon.event.model.*;
import com.hackathon.event.model.enumeration.StatusType;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.repository.ParticipantRepository;
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
    public void saveProgress(Long eventId,  Long participantId,
                      Integer week_no, ProgressFlowRequestDto progressFlowRequestDto){
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        Participant participant = participantRepository.findById(participantId).orElseThrow
                (() -> new EntityNotFoundException("Registration not found"));

        for(Progress progress: participant.getProgress()){
            if(week_no.equals(progress.getWeek())){
                Flow flow = new Flow();
                StatusType statusType = StatusType.valueOf(progressFlowRequestDto.getStatus());
                flow.setStatus(statusType);
                flow.setComment(progressFlowRequestDto.getComment());
                progress.getFlow().add(flow);
            }
        }
    }
}
