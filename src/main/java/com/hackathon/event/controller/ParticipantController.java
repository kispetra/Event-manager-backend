package com.hackathon.event.controller;

import com.hackathon.event.dto.FlowRequestDto;
import com.hackathon.event.dto.ParticipantResponseDto;
import com.hackathon.event.mapper.ParticipantMapper;
import com.hackathon.event.model.Participant;
import com.hackathon.event.model.Progress;
import com.hackathon.event.repository.ParticipantRepository;
import com.hackathon.event.repository.ProgressRepository;
import com.hackathon.event.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    private final ParticipantRepository participantRepository;
    private final ProgressRepository progressRepository;
    private final ParticipantMapper participantMapper;
    @PutMapping("/event/{eventId}/participants/{participantId}/week/{week_no}")
    public ResponseEntity<String> saveProgress(@PathVariable Long eventId, @PathVariable Long participantId,
                                       @PathVariable Integer week_no, @RequestBody FlowRequestDto flowRequestDto){
           return participantService.saveProgress(eventId,participantId,week_no, flowRequestDto);
    }
    @GetMapping("/event/{eventId}/participants/{participantId}/progress")
    public List<Progress> getProgress(@PathVariable Long eventId, @PathVariable Long participantId){
        return participantService.getProgress(eventId,participantId);
    }
    @GetMapping("/event/{eventId}/participants")
    public Page<ParticipantResponseDto> getAllParticipants(@PathVariable Long eventId, Pageable pageable){
        return participantService.getAllParticipants(eventId,pageable);
    }

    @GetMapping("/event/{eventId}/participants/{participantId}")
    public ResponseEntity<ParticipantResponseDto> getById(@PathVariable Long eventId, @PathVariable Long participantId){
        Optional<Participant> participantOptional = participantRepository.findById(participantId);

        if (participantOptional.isPresent()) {
            Participant participant = participantOptional.get();
            ParticipantResponseDto participantResponseDto = participantMapper.toDto(participant);
            return ResponseEntity.ok(participantResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
