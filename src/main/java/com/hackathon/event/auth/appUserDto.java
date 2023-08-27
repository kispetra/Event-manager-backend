package com.hackathon.event.auth;

import lombok.Data;

@Data
public class appUserDto {

    private int userid;
    private String username;
    private String email;
    private String password;

    public appUserDto() {
    }

    public appUserDto(int userid, String username, String email, String password) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}