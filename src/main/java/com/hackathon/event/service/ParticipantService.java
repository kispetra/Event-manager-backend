package com.hackathon.event.service;

import com.hackathon.event.dto.FlowRequestDto;
import com.hackathon.event.dto.ParticipantResponseDto;
import com.hackathon.event.model.Progress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ParticipantService {
    ResponseEntity<String> saveProgress(Long eventId, Long participantId, Integer week_no, FlowRequestDto flowRequestDto);

    Page<ParticipantResponseDto> getAllParticipants(Long eventId, Pageable pageable);
    List<Progress> getProgress(Long eventId, Long participantId);

}

