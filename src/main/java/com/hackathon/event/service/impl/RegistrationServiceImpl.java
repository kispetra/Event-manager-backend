package com.hackathon.event.service.impl;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.mapper.RegistrationMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.model.enumeration.SkillType;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final SkillRepository skillRepository;


    @Override
    public void save(Long eventId, RegistrationRequestDto registrationRequestDto) {
        Registration registration = registrationMapper.toEntity(registrationRequestDto);

        Name name = registration.getPersonal().getName();
        name.setPersonal(registration.getPersonal());
        registration.getPersonal().setName(name);

        Education education = registration.getPersonal().getEducation();
        education.setPersonal(registration.getPersonal());
        registration.getPersonal().setEducation(education);

        Personal personal = registration.getPersonal();
        personal.setRegistration(registration);
        registration.setPersonal(personal);

        Experience experience = registration.getExperience();
        experience.setRegistration(registration);
        registration.setExperience(experience);

        registrationRepository.save(registration);

        for(Skill skill : registration.getExperience().getSkills()){
            skill.setExperience(experience);
            skillRepository.save(skill);
        }

        //TODO: return response entity
    }
}
