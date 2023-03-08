package com.hackathon.event.service;

public interface EmailService {
    void send(String receiver, String subject, String text);
}
