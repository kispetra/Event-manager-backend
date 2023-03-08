package com.hackathon.event.service;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.TeamResponseDto;
import com.hackathon.event.dto.TeamUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface EventService {

    void save(EventRequestDto eventRequestDto);
    TeamUpResponseDto teamUp(Long eventId);
}
