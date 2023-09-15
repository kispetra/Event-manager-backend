package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExperienceRequestDto {
    private Integer years;
    private List<SkillNameRequestDto> skills;
    private String summary;

}
