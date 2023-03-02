package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalRequestDto {
    NameRequestDto name;
    private String email;
    private String phone;
    EducationRequestDto education;
    private String summary;
}
