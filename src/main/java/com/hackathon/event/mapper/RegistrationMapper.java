package com.hackathon.event.mapper;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.model.*;
import com.hackathon.event.model.enumeration.SkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        List<Skill> skills = new ArrayList<>();
        for(String skill : requestDto.getExperience().getSkills()){
            if(skillTypeExists(skill)){
                Skill skillToSave = new Skill();
                skillToSave.setSkillType(SkillType.valueOf(skill));
                skills.add(skillToSave);
            }
        }

        for (Skill skill : skills){
            skill.setExperience(experience);
        }

        experience.setSkills(skills);

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

    // TODO: move this to validation
    private boolean skillTypeExists(String skillType){
        for (SkillType skill : SkillType.values()) {
            if (skill.name().equals(skillType)) {
                return true;
            }
        }
        return false;
    }
}
