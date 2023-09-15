package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserInfoResponse {
    public Long id;
    public String username;
    public String email;
}
