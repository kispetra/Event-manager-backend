package com.hackathon.event.service.impl;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.mapper.RegistrationMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.model.enumeration.SkillType;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.RegistrationService;
import com.hackathon.event.util.ScoringEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final RegistrationMapper registrationMapper;
    private final SkillRepository skillRepository;


    @Override
    public void save(Long eventId, RegistrationRequestDto registrationRequestDto) {
        ScoringEngine scoringEngine = new ScoringEngine();
        Integer score = scoringEngine.CalculateScore(registrationRequestDto);

        Event event=eventRepository.findById(eventId).orElseThrow(()-> new EntityNotFoundException());
        Registration registration = registrationMapper.toEntity(registrationRequestDto, event);

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
        registration.setScore(score);

        registrationRepository.save(registration);

        for(Skill skill : registration.getExperience().getSkills()){
            skill.setExperience(experience);
            skillRepository.save(skill);
        }

        //TODO: return response entity
    }

    @Override
    public void deleteById(Long eventId, Long registrationId){

        Event event = eventRepository.findById(eventId).orElseThrow
                (()-> new EntityNotFoundException("Event not found"));
        Registration registration = registrationRepository.findById(registrationId).orElseThrow
                (()-> new EntityNotFoundException("Registration not found"));

        event.getRegistrations().remove(registration);
        registrationRepository.delete(registration);

    }
}
