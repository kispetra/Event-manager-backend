package com.hackathon.event.mapper;

import com.hackathon.event.dto.SkillNameRequestDto;
import com.hackathon.event.dto.SkillRequestDto;
import com.hackathon.event.dto.SkillResponseDto;
import com.hackathon.event.model.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class SkillMapper {

    public Skill toEntityName(SkillNameRequestDto skillNameRequestDto){
        Skill skill =new Skill();
        skill.setName(skillNameRequestDto.getName());
        return skill;
    }
    public Skill toEntity(SkillRequestDto skillRequestDto){
        Skill skill= new Skill();
        skill.setName(skillRequestDto.getName());
        skill.setPoints(skillRequestDto.getPoints());

        return skill;
    }
    public SkillResponseDto toDto(Skill skill){
        SkillResponseDto skillResponseDto = new SkillResponseDto();
        skillResponseDto.setName(skill.getName());
        skillResponseDto.setPoints(skill.getPoints());

        return skillResponseDto;
    }
}
