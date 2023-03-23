package com.hackathon.event.controller;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.TeamResponseDto;
import com.hackathon.event.dto.TeamUpResponseDto;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/event")
    public ResponseEntity<String> save(@RequestBody EventRequestDto eventRequestDto){
        return eventService.save(eventRequestDto);
    }

    @PutMapping("/event/{eventId}/invite")
    public ResponseEntity<?> invite(@PathVariable Long eventId){
        return eventService.invite(eventId);
    }

    @PutMapping("/event/{eventId}/teamUp")
    public ResponseEntity<?> teamUp(@PathVariable Long eventId){
        return eventService.teamUp(eventId);
    }
}
