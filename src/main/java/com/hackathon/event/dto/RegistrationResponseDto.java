package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistrationResponseDto {
     Long registrationId;
    PersonalResponseDto personal;
    ExperienceResponseDto experience;
    private String motivation;
    private Integer score;
    List<CommentResponseDto> comments;
}
