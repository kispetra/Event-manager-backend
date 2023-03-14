package com.hackathon.event.dto;

import com.hackathon.event.model.Mentor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamsResponseDto {
    private String name;
    private List<MentorResponseDto> mentors;
    private List<ParticipantResponseDto> members;

}
