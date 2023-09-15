package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRequestDto {
    private String login;
    private String firstName;
    private String lastName;
    private String address;
    private String houseNumber;
    private String country;
    private String email;
    private String password;

}