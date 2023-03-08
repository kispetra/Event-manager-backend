package com.hackathon.event.controller;

import com.hackathon.event.dto.ConfirmationRequestDto;
import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.dto.RegistrationResponseDto;
import com.hackathon.event.dto.CommentRequestDto;
import com.hackathon.event.repository.SkillRepository;
import com.hackathon.event.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final SkillRepository skillRepository;

    @PostMapping("/event/{eventId}/registrations")
    public void save(@PathVariable Long eventId, @RequestBody RegistrationRequestDto registrationRequestDto){
        registrationService.save(eventId,registrationRequestDto);
    }

    @DeleteMapping("/event/{eventId}/registrations/{registrationId}")
    public void deleteById(@PathVariable Long eventId,@PathVariable Long registrationId){
        registrationService.deleteById(eventId,registrationId);
    }

    @PutMapping("/event/{eventId}/registrations/{registrationId}/score")
    public ResponseEntity<String> score(@PathVariable Long eventId, @PathVariable Long registrationId, @RequestBody CommentRequestDto scoreRequestDto){
        return registrationService.score(eventId,registrationId,scoreRequestDto);
    }
    @GetMapping("/event/{eventId}/registrations/{registrationId}")
    public RegistrationResponseDto fetchById(@PathVariable Long eventId, @PathVariable Long registrationId){
        return registrationService.fetchById(eventId,registrationId);
    }

    @GetMapping("/event/{eventId}/registrations")
    public Page<RegistrationResponseDto> getAllRegistrations(@PathVariable Long eventId, Pageable pageable){
        return registrationService.getAllRegistrations(eventId, pageable);
    }
    @PatchMapping("/event/{eventId}/registrations/{registrationId}")
    public void patchById(@PathVariable Long eventId, @PathVariable Long registrationId, @RequestBody ConfirmationRequestDto confirmationRequestDto){
        registrationService.patchById(eventId, registrationId, confirmationRequestDto);

    }
}
