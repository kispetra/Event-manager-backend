package com.hackathon.event.service.impl;

import com.hackathon.event.dto.*;
import com.hackathon.event.mapper.ParticipantMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.model.enumeration.StatusType;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private final ParticipantMapper participantMapper;

    @Override
    public ResponseEntity<String> saveProgress(Long eventId, Long participantId,
                                       Integer week_no, FlowRequestDto flowRequestDto){
        if (flowRequestDto == null || flowRequestDto.getComment() == null) {
            return ResponseEntity.badRequest().body("FlowRequestDto and comment must not be null.");
        }
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        Participant participant = participantRepository.findById(participantId).orElseThrow
                (() -> new EntityNotFoundException("Registration not found"));

        if(flowRequestDto.getComment().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        if(week_no>event.getWeeks()){
            return ResponseEntity.status(405).body("Week number is out of bounds.");
        }

        List<Progress> progresses = participant.getProgress();

        Flow flow = new Flow();
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
            flow.setProgress(progress);
            progress.setParticipant(participant);
            progress.getFlow().add(flow);
        }
        participantRepository.save(participant);
        progressRepository.save(progress);
        flowRepository.save(flow);

        return ResponseEntity.ok().body("Saved.");
    }


    @Override
    public Page<ParticipantResponseDto> getAllParticipants(Long eventId, Pageable pageable){
        Page<Participant> allParticipantsByEventId = participantRepository.getAllParticipantsByEventId(eventId, pageable);
        return participantRepository.getAllParticipantsByEventId(eventId, pageable).map(participantMapper::toDto);
    }
    public List<Progress> getProgress( Long eventId,  Long participantId) {
        return participantRepository.findAllProgressByParticipantId(participantId);
    }
}
