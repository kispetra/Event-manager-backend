package com.hackathon.event.service.impl;

import com.hackathon.event.model.Event;
import com.hackathon.event.model.Participant;
import com.hackathon.event.model.Registration;
import com.hackathon.event.model.Team;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.repository.ParticipantRepository;
import com.hackathon.event.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public void send(String receiver, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    // runs every day
//    @Scheduled(fixedDelay = 3600000)
//    @Transactional
//    public void autoInvitesScheduledTask()
//    {
//        //List<Event> events = eventRepository.findAllWhereConfirmationDateExpiredAndInvitesSentFalse();
//        List<Event> events = eventRepository.findAll();
//        for(Event event : events) {
//            Collections.sort(event.getRegistrations(), Comparator.comparing(Registration::getScore).reversed());
//            if(!event.getInvitesSent() && event.getConfirmationNotAfter().before(new Date())) {
//                Integer count = 0;
//                for(Registration registration : event.getRegistrations()) {
//                    Participant participant = new Participant();
//                    participant.setEmail(registration.getPersonal().getEmail());
//                    participant.setRegistration(registration);
//                    participantRepository.save(participant);
//
//                    String emailSubject = "Invitation to event";
//                    String emailText = "" +
//                            "Dear " + registration.getPersonal().getEmail() + ", \n\n" +
//                            "Invited to event" +
//                            "\n\n Lp, Your organiser";
//
//                    //      emailService.send(participant.getEmail(), emailSubject, emailText);
//                    System.out.println("Automatski poslan invite: " + registration.getPersonal().getEmail());
//
//                    count++;
//                    if (count>=event.getMaxParticipants()) {
//                        break;
//                    }
//                }
//                event.setInvitesSent(true);
//                eventRepository.save(event);
//            }
//        }
//    }
}
