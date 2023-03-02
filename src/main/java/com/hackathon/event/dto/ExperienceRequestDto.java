package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExperienceRequestDto {
    private Integer years;
    private SkillRequestDto skills;
    private String repositoryUrl;
    private String summary;

}
