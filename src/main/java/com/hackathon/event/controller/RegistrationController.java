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



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final SkillRepository skillRepository;

    @PostMapping("/event/{eventId}/registrations")
    public ResponseEntity<String> save(@PathVariable Long eventId, @RequestBody RegistrationRequestDto registrationRequestDto){
         return registrationService.save(eventId, registrationRequestDto);
    }
    @DeleteMapping("/event/{eventId}/registrations/{registrationId}")
    public ResponseEntity<String> deleteById(@PathVariable Long eventId, @PathVariable Long registrationId){
        return registrationService.deleteById(eventId, registrationId);
    }
    @PutMapping("/event/{eventId}/registrations/{registrationId}")
    public ResponseEntity<String> score(@PathVariable Long eventId, @PathVariable Long registrationId, @RequestBody CommentRequestDto scoreRequestDto){
        return registrationService.score(eventId, registrationId, scoreRequestDto);
    }
    @GetMapping("/event/{eventId}/registrations")
    public Page<RegistrationResponseDto> getAllRegistrations(@PathVariable Long eventId, Pageable pageable){
        return registrationService.getAllRegistrations(eventId, pageable);
    }
    @PatchMapping("/event/{eventId}/registrations/{registrationId}")
    public ResponseEntity<String> patchById(@PathVariable Long eventId, @PathVariable Long registrationId, @RequestBody ConfirmationRequestDto confirmationRequestDto){
        return registrationService.patchById(eventId, registrationId, confirmationRequestDto);
    }
}
