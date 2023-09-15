package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class AppUserResponseDto {
    private String login;
    private String firstName;
    private String lastName;
    private String address;
    private String houseNumber;
    private String country;
    private String email;
    private String password;
    private List<EventRequestDto> events;
    private List<RegistrationRequestDto> registrations;
    private List<ParticipantRequestDto> participants;
}
