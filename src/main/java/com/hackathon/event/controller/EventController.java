package com.hackathon.event.controller;

import com.hackathon.event.dto.*;
import com.hackathon.event.model.Event;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @GetMapping("/event/all")
    public List<EventResponseDto> getAll() {
        return eventService.getAll();
    }

    @PostMapping("/event")
    public ResponseEntity<String> save(@RequestBody EventRequestDto eventRequestDto){
        return eventService.save(eventRequestDto);
    }

    @PutMapping("/event/{eventId}/invite")
    public ResponseEntity<?> invite(@PathVariable Long eventId){
        return eventService.invite(eventId);
    }


}
