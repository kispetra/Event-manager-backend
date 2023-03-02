package com.hackathon.event.dto;

import com.hackathon.event.model.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventRequestDto {
    private String name;
    private Integer maxParticipants;
    private List<TeamRequestDto> teams;
    private Date registrationsNotBefore;
    private Date registrationsNotAfter;
    private Date confirmationNotAfter;
    private Date startDate;
    private Integer weeks;
}
