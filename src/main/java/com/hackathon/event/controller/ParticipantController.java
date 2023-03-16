package com.hackathon.event.controller;

import com.hackathon.event.dto.AllTeamsResponseDto;
import com.hackathon.event.dto.FlowRequestDto;
import com.hackathon.event.dto.ParticipantResponseDto;
import com.hackathon.event.dto.TeamResponseDto;
import com.hackathon.event.repository.ProgressRepository;
import com.hackathon.event.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    private final ProgressRepository progressRepository;
    @PutMapping("/event/{eventId}/participants/{participantId}/week/{week_no}")
    public ResponseEntity<String> saveProgress(@PathVariable Long eventId, @PathVariable Long participantId,
                                       @PathVariable Integer week_no, @RequestBody FlowRequestDto flowRequestDto){
           return participantService.saveProgress(eventId,participantId,week_no, flowRequestDto);
    }

    @GetMapping("/event/{eventId}/participants")
    public Page<ParticipantResponseDto> getAllParticipants(@PathVariable Long eventId, Pageable pageable){
        return participantService.getAllParticipants(eventId,pageable);
    }
    @GetMapping("/event/{eventId}/teams")
    public AllTeamsResponseDto getTeams(@PathVariable Long eventId){
        return participantService.getTeams(eventId);
    }
}
