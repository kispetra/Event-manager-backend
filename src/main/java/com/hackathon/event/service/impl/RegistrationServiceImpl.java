package com.hackathon.event.service.impl;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.mapper.RegistrationMapper;
import com.hackathon.event.model.Registration;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final PersonalRepository personalRepository;
    private final ExperienceRepository experienceRepository;
    private final RegistrationMapper registrationMapper;
    private final NameRepository nameRepository;
    private final EducationRepository educationRepository;

    @Override
    public void save(Long eventId, RegistrationRequestDto registrationRequestDto) {
        Registration registration = registrationMapper.toEntity(registrationRequestDto);

        nameRepository.save(registration.getPersonal().getName());
        educationRepository.save(registration.getPersonal().getEducation());
        personalRepository.save(registration.getPersonal());

        experienceRepository.save(registration.getExperience());

        registrationRepository.save(registration);
//        return ResponseEntity<>
    }
}
