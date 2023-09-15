package com.hackathon.event.controller;

import com.hackathon.event.dto.*;
import com.hackathon.event.mapper.ParticipantMapper;
import com.hackathon.event.model.Participant;
import com.hackathon.event.repository.ParticipantRepository;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    @GetMapping("/event/all")
    public ResponseEntity<HttpResponse> getAll(@RequestParam Optional<String> name,
                                               @RequestParam Optional<Integer> page,
                                               @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("page", eventService.getAll(name.orElse(""),page.orElse(0),size.orElse(10))))
                        .message("Event retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @GetMapping("/event/all/{eventId}")
    public EventResponseDto getById (@PathVariable Long eventId){
        return eventService.getById(eventId);
    }

    @PostMapping("/event")
    public ResponseEntity<String> save(@RequestBody EventRequestDto eventRequestDto){
        return eventService.save(eventRequestDto);
    }

    @PutMapping("/event/{eventId}/invite")
    public ResponseEntity<?> invite(@PathVariable Long eventId){
        return eventService.invite(eventId);
    }
    @GetMapping("/event/{eventId}/invited")
    public List<ParticipantResponseDto> getInvitedParticipants(@PathVariable Long eventId) {
       List<Participant> participants= participantRepository.findAllByEventId(eventId);
       List<ParticipantResponseDto> dtos= new ArrayList<ParticipantResponseDto>();

        for (Participant participant: participants) {
            ParticipantResponseDto participantResponseDto = participantMapper.toDto(participant);
            dtos.add(participantResponseDto);

        }
        return dtos;
    }

}
