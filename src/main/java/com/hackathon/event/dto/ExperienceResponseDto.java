package com.hackathon.event.dto;

import com.hackathon.event.model.Skill;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExperienceResponseDto {
    private Integer years;
    private List<String> skills;
    private String repositoryUrl;
    private String summary;
}
