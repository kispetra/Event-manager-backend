package com.hackathon.event.service;

import com.hackathon.event.dto.RegistrationRequestDto;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {

    ResponseEntity<String> save(Long eventId, RegistrationRequestDto registrationRequestDto);
}
