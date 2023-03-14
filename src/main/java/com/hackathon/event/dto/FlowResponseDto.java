package com.hackathon.event.dto;

import com.hackathon.event.model.enumeration.StatusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowResponseDto {

    private StatusType status;
    private String comment;
}
