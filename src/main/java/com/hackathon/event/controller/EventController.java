package com.hackathon.event.controller;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/event")
    public void save(@RequestBody EventRequestDto eventRequestDto){
        eventService.save(eventRequestDto);
    }
}
