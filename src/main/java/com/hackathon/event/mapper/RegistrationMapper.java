package com.hackathon.event.mapper;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationMapper {
    public Registration toEntity(RegistrationRequestDto requestDto){
        Registration registration = new Registration();
        registration.setPreferredOs(requestDto.getPreferredOS());
        registration.setMotivation(requestDto.getMotivation());

        Experience experience = new Experience();
        experience.setYears(requestDto.getExperience().getYears());
        experience.setRepositoryUrl(requestDto.getExperience().getRepositoryUrl());
        experience.setSummary(requestDto.getExperience().getSummary());
//        experience.setSkills(requestDto.getExperience().getSkills());

        Personal personal = new Personal();

        Name name = new Name();
        name.setFirstName(requestDto.getPersonal().getName().getFirst());
        name.setLastName(requestDto.getPersonal().getName().getLast());

        personal.setName(name);
        personal.setEmail(requestDto.getPersonal().getEmail());
        personal.setPhone(requestDto.getPersonal().getPhone());

        Education education = new Education();
        education.setFaculty(requestDto.getPersonal().getEducation().getFaculty());
        education.setYear(requestDto.getPersonal().getEducation().getYear());
        personal.setEducation(education);

        personal.setSummary(requestDto.getPersonal().getSummary());

        registration.setExperience(experience);
        registration.setPersonal(personal);

        return registration;
    }
}
