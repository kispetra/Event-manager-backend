package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProgressResponseDto {

    private Integer week_no;
    private List<FlowResponseDto> flowList;
}
