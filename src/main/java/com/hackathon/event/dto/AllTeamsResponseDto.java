package com.hackathon.event.dto;

import com.hackathon.event.model.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllTeamsResponseDto {
    private List<TeamsResponseDto> teams;

}
