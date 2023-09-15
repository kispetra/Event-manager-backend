package com.hackathon.event.service;

import com.hackathon.event.dto.*;
import com.hackathon.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {

    ResponseEntity<String> save(EventRequestDto eventRequestDto);
    ResponseEntity<?> invite(Long eventId);
    Page<EventResponseDto> getAll(String name, int page, int size);
    List<EventResponseDto> getAllEventsOfUser(Long userId);
    ResponseEntity<String>  saveEventOfUser(Long userId, EventRequestDto eventRequestDto);
    EventResponseDto getEventByIdOfUser( Long eventId);
    ResponseEntity<String> updateEvent(Long eventId, EventUpdateRequestDto eventUpdateRequest);
    ResponseEntity<String>  deleteEventByIdOfUser(Long eventId);
    EventResponseDto getById(Long eventId);



}
