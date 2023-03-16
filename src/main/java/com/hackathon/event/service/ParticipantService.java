package com.hackathon.event.service;

import com.hackathon.event.dto.AllTeamsResponseDto;
import com.hackathon.event.dto.FlowRequestDto;
import com.hackathon.event.dto.ParticipantResponseDto;
import com.hackathon.event.dto.TeamResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ParticipantService {
    ResponseEntity<String> saveProgress(Long eventId, Long participantId, Integer week_no, FlowRequestDto flowRequestDto);

    Page<ParticipantResponseDto> getAllParticipants(Long eventId, Pageable pageable);

    AllTeamsResponseDto getTeams(Long eventId);
}

