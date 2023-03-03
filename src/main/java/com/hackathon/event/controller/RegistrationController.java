package com.hackathon.event.controller;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.model.Skill;
import com.hackathon.event.model.enumeration.SkillType;
import com.hackathon.event.repository.SkillRepository;
import com.hackathon.event.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final SkillRepository skillRepository;

    @PostMapping("/event/{eventId}/registrations")
    public void save(@PathVariable Long eventId, @RequestBody RegistrationRequestDto registrationRequestDto){
        registrationService.save(eventId,registrationRequestDto);
    }
}
