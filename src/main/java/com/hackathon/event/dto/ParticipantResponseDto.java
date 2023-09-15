package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParticipantResponseDto {
    private Long participantId;
    private PersonalResponseDto personal;
    private ExperienceResponseDto experience;
    private Integer score;
    private List<CommentResponseDto> comments;
    private Boolean participation;
    private List<ProgressResponseDto> progress;
}
