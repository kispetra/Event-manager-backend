package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalResponseDto {
    NameResponseDto name;
    private String email;
    private String phone;
    EducationResponseDto education;
    private String summary;
}
