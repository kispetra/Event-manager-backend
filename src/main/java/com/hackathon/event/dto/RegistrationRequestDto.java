package com.hackathon.event.dto;

import com.hackathon.event.model.Personal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDto {
    PersonalRequestDto personal;
    ExperienceRequestDto experience;

    private String motivation;
    private String preferredOs;

}
