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

     List<EventResponseDto> getAll();


}
