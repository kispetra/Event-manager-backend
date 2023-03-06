package com.hackathon.event.controller;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.model.Skill;
import com.hackathon.event.model.enumeration.SkillType;
import com.hackathon.event.repository.SkillRepository;
import com.hackathon.event.service.RegistrationService;
import lombok.RequiredArgsConstructor;
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
}
