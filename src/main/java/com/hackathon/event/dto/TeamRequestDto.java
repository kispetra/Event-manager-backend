package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamRequestDto {
    private String name;
    private List<String> mentors;
}
