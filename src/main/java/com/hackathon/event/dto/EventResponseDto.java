package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class EventResponseDto {
    private Long eventId;
    private String name;
    private String description;
    private Integer maxParticipants;
    private Date registrationsNotBefore;
    private Date registrationsNotAfter;
    private Date confirmationNotAfter;
    private Date startDate;
    private Integer weeks;
    private List<SkillResponseDto> skills;
}
