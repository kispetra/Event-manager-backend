package com.hackathon.event.service;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.ParticipantListRequestDto;
import com.hackathon.event.dto.ParticipantRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {

    void save(EventRequestDto eventRequestDto);

    ResponseEntity<String> invite(Long eventId, ParticipantListRequestDto participantListRequestDto);
}
