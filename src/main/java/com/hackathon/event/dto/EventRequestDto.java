package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventRequestDto {
    private String name;
    private String description;
    private Integer maxParticipants;
    private Date registrationsNotBefore;
    private Date registrationsNotAfter;
    private Date confirmationNotAfter;
    private Date startDate;
    private Integer weeks;
    private List<SkillRequestDto> skills;
}
