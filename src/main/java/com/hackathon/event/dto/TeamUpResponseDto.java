package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamUpResponseDto {
    List<TeamResponseDto> teams;
}
