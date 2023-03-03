package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDto {
    PersonalRequestDto personal;
    ExperienceRequestDto experience;
    private String motivation;
    private String preferredOS;

}
