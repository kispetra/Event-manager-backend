package com.hackathon.event.dto;

import com.hackathon.event.model.Skill;
import com.hackathon.event.model.enumeration.SkillType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExperienceRequestDto {
    private Integer years;
    private List<String> skills;
    private String repositoryUrl;
    private String summary;

}
