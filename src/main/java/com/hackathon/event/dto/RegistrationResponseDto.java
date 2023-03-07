package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistrationResponseDto {
    PersonalResponseDto personal;
    ExperienceResponseDto experience;
    private String motivation;
    private String preferredOS;
    private Integer score;
    List<CommentResponseDto> comments;
}
