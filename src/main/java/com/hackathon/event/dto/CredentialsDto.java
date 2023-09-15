package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CredentialsDto {
    public String login;
    public char[] password;
}
