package com.hackathon.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ErrorDto {
    public String message;
    public ErrorDto(String message){
        this.message=message;
    }
}
