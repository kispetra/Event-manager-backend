package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmationRequestDto {
    private Boolean participation;
    private Boolean kickoff;
    private String tshirt;
    private String gitlab;

}

