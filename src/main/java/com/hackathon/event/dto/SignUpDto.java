package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SignUpDto {
    private String login;
    private String firstname;
    private String lastname;
    private String address;
    private String houseNumber;
    private String country;
    private String email;
    private String password;
}
