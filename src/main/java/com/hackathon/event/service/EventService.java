package com.hackathon.event.service;

import com.hackathon.event.dto.EventRequestDto;
import org.springframework.http.ResponseEntity;

public interface EventService {

    void save(EventRequestDto eventRequestDto);
}
