package com.hackathon.event.dto;

import com.hackathon.event.model.Event;
import com.hackathon.event.model.Mentor;
import com.hackathon.event.model.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamRequestDto {
    private String name;
    private List<String> mentors;
}
