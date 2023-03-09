package com.hackathon.event.controller;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/event")
    public void save(@RequestBody EventRequestDto eventRequestDto){
        eventService.save(eventRequestDto);
    }

    @PutMapping("/event/{eventId}/invite")
    public ResponseEntity<?> invite(@PathVariable Long eventId){
        return eventService.invite(eventId);
    }
}
