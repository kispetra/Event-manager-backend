package com.hackathon.event.service.impl;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public ResponseEntity<String> save(Long eventId, RegistrationRequestDto registrationRequestDto) {
        return null;
    }
}
