package com.hackathon.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class EventUpdateRequestDto {
    private String name;
    private String description;
    private Integer maxParticipants;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String registrationsNotBefore;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String registrationsNotAfter;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String confirmationNotAfter;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String startDate;
    private Integer weeks;


}
