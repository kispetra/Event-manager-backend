package com.hackathon.event.dto;

import com.hackathon.event.model.enumeration.Skills;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SkillRequestDto {
    private Skills skill;
}
