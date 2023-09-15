package com.hackathon.event.service;

import com.hackathon.event.dto.ConfirmationRequestDto;
import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.dto.RegistrationResponseDto;
import com.hackathon.event.dto.CommentRequestDto;
import com.hackathon.event.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


import java.util.List;


public interface RegistrationService {
    List<Skill> getSkillsByEventId(Long eventId);

    ResponseEntity<String> save(Long eventId, RegistrationRequestDto registrationRequestDto);
    ResponseEntity<String> deleteById(Long eventId, Long registrationId);

    ResponseEntity<String> score(Long eventId, Long registrationId, CommentRequestDto scoreRequestDto);
    RegistrationResponseDto fetchById(Long eventId, Long registrationId);
    Page<RegistrationResponseDto> getAllRegistrations(Long eventId, Pageable pageable);
    List<RegistrationResponseDto> getAllUserRegistrations(Long eventId);
    ResponseEntity<String> patchById(Long eventId, Long registrationId, ConfirmationRequestDto confirmationRequestDto);
}
