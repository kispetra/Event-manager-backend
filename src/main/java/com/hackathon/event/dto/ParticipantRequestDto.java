package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantRequestDto {
    private String email;
    private Integer registrationId;
}
