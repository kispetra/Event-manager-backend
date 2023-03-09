package com.hackathon.event.controller;

import com.hackathon.event.dto.ProgressFlowRequestDto;
import com.hackathon.event.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    @PutMapping("/event/{eventId}/participants/{participantId}/week/{week_no}")
    public void saveProgress(@PathVariable Long eventId, @PathVariable Long participantId,
                             @PathVariable Integer week_no, @RequestBody ProgressFlowRequestDto progressFlowRequestDto){
            participantService.saveProgress(eventId,participantId,week_no,progressFlowRequestDto);
    }
}
