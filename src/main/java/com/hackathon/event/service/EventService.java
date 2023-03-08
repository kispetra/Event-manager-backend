package com.hackathon.event.service;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.TeamResponseDto;
import com.hackathon.event.dto.TeamUpResponseDto;
import com.hackathon.event.dto.ParticipantListRequestDto;
import com.hackathon.event.dto.ParticipantRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {

    void save(EventRequestDto eventRequestDto);
    TeamUpResponseDto teamUp(Long eventId);

    ResponseEntity<String> invite(Long eventId, ParticipantListRequestDto participantListRequestDto);
}
