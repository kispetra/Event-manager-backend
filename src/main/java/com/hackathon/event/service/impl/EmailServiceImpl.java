package com.hackathon.event.service.impl;

import com.hackathon.event.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void send(String receiver, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
