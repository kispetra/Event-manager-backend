package com.hackathon.event.service;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.TeamResponseDto;
import com.hackathon.event.dto.TeamUpResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {

    ResponseEntity<String> save(EventRequestDto eventRequestDto);

    ResponseEntity<?> invite(Long eventId);

    ResponseEntity<?> teamUp(Long eventId);

}
