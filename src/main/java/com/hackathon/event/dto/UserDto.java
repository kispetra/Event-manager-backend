package com.hackathon.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userid;
    private String firstname;
    private String lastname;
    private String login;
    private String address;
    private String houseNumber;
    private String country;
    private String email;
    private String token;

}
