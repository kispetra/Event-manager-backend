package com.hackathon.event.dto;

import com.hackathon.event.model.Participant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParticipantListRequestDto {
    List<ParticipantRequestDto> participants;
}
