package com.hackathon.event.exceptions;

public class GenericValidationException extends RuntimeException{
    public GenericValidationException(String message){
        super(message);
    }
}