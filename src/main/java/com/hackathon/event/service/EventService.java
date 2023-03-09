package com.hackathon.event.service;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.ParticipantListRequestDto;
import com.hackathon.event.dto.ParticipantRequestDto;
import com.hackathon.event.dto.TeamUpResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {

    void save(EventRequestDto eventRequestDto);

    ResponseEntity<?> invite(Long eventId);

    List<TeamUpResponseDto> teamUp(Long eventId);

}
