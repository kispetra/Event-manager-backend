package com.hackathon.event.dto;

import com.hackathon.event.model.Personal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParticipantResponseDto {

    private PersonalResponseDto personal;
    private ExperienceResponseDto experience;
    private Integer score;
    private List<CommentResponseDto> comments;
    private Boolean participation;
    private Boolean kickoff;
    private String tshirt;
    private  String gitlab;
    private List<ProgressResponseDto> progress;
}
