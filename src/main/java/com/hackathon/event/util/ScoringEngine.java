package com.hackathon.event.util;

import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.dto.SkillNameRequestDto;
import com.hackathon.event.dto.SkillRequestDto;
import com.hackathon.event.mapper.SkillMapper;
import com.hackathon.event.model.Skill;
import com.hackathon.event.repository.SkillRepository;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ScoringEngine {
    private final SkillRepository skillRepository;
    private Integer yearsOfEducationMultiplier = 2;
    private Integer yearsOfExperienceMultiplier = 5;


    public Integer CalculateScore(RegistrationRequestDto registrationRequest) {
        Integer yearsOfEducationPoints = registrationRequest.getPersonal().getEducation().getYear() * yearsOfEducationMultiplier;
        Integer yearsOfExperiencePoints = registrationRequest.getExperience().getYears() * yearsOfExperienceMultiplier;
        int sum=0;
        for( SkillNameRequestDto skillDto : registrationRequest.getExperience().getSkills()) {
            Skill skill = skillRepository.findFirstByName(skillDto.getName());
            int point = skill.getPoints();
            sum += point;
        }

        return yearsOfEducationPoints + yearsOfExperiencePoints + sum;
    }
}

