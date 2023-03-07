package com.hackathon.event.mapper;

import com.hackathon.event.dto.*;
import com.hackathon.event.model.*;
import com.hackathon.event.model.enumeration.SkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationMapper {
    private final CommentMapper commentMapper;
    public Registration toEntity(RegistrationRequestDto requestDto, Event event){
        Registration registration = new Registration();
        registration.setEvent(event);
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
    public RegistrationResponseDto toDto(Registration registration){

        RegistrationResponseDto registrationResponseDto= new RegistrationResponseDto();
        registrationResponseDto.setPreferredOS(registration.getPreferredOs());
        registrationResponseDto.setMotivation(registration.getMotivation());
        registrationResponseDto.setScore(registration.getScore());
        List<CommentResponseDto> comments= new ArrayList<>();
        for(Comment comment: registration.getComment()){
            CommentResponseDto commentResponseDto= commentMapper.toDto(comment);
            comments.add(commentResponseDto);
        }
        registrationResponseDto.setComments(comments);


        PersonalResponseDto personalResponseDto= new PersonalResponseDto();
        NameResponseDto nameResponseDto= new NameResponseDto();
        nameResponseDto.setFirst(registration.getPersonal().getName().getFirstName());
        nameResponseDto.setLast(registration.getPersonal().getName().getLastName());
        personalResponseDto.setName(nameResponseDto);

        personalResponseDto.setEmail(registration.getPersonal().getEmail());
        personalResponseDto.setPhone(registration.getPersonal().getPhone());

        EducationResponseDto educationResponseDto= new EducationResponseDto();
        educationResponseDto.setFaculty(registration.getPersonal().getEducation().getFaculty());
        educationResponseDto.setYear(registration.getPersonal().getEducation().getYear());
        personalResponseDto.setEducation(educationResponseDto);

        personalResponseDto.setSummary(registration.getPersonal().getSummary());
        registrationResponseDto.setPersonal(personalResponseDto);


        ExperienceResponseDto experienceResponseDto= new ExperienceResponseDto();
        experienceResponseDto.setYears(registration.getExperience().getYears());
        List<String> skills = new ArrayList<>();
        for(Skill skill : registration.getExperience().getSkills()){
                String skillToSave = skill.getSkillType().name();
                //skillToSave.setSkillId(skill.getSkillId());
                //skillToSave.setSkillType(skill.getSkillType());
                skills.add(skillToSave);
        }
        experienceResponseDto.setSkills(skills);
        experienceResponseDto.setRepositoryUrl(registration.getExperience().getRepositoryUrl());
        experienceResponseDto.setSummary(registration.getExperience().getSummary());
        registrationResponseDto.setExperience(experienceResponseDto);

        return registrationResponseDto;

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
