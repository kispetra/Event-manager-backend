package com.hackathon.event.service.impl;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.ParticipantListResponseDto;
import com.hackathon.event.mapper.EventMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.EmailService;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final TeamRepository teamRepository;
    private final MentorRepository mentorRepository;
    private final RegistrationRepository registrationRepository;
    private final ParticipantRepository participantRepository;
    private final EmailService emailService;

    public void save(EventRequestDto eventRequestDto){
        Event event = eventMapper.toEntity(eventRequestDto);
        eventRepository.save(event);
        for (Team team : event.getTeams()){
            team.setEvent(event);
            teamRepository.save(team);
            for(Mentor mentor : team.getMentors()){
                mentor.setTeam(team);
                mentorRepository.save(mentor);
            }
        }
    }

    @Override
    public ResponseEntity<?> invite(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));

        if(event.getInvitesSent()){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Invites already sent");
        }

        ParticipantListResponseDto participantListResponseDto = new ParticipantListResponseDto();
        List<Participant> participants = new ArrayList<>();

        Collections.sort(event.getRegistrations(), Comparator.comparing(Registration::getScore).reversed());

        for (Registration registration: event.getRegistrations()) {
            Participant participant = new Participant();
            participant.setEmail(registration.getPersonal().getEmail());
            participant.setRegistration(registration);
            participantRepository.save(participant);

           participants.add(participant);

            String emailSubject = "Invitation to event";
            String emailText = "" +
                    "Dear " + registration.getPersonal().getEmail() + ", \n\n" +
                    "Invited to event" +
                    "\n\n Lp, Your organiser";

      //      emailService.send(participant.getEmail(), emailSubject, emailText);
            System.out.println("Poslan mail"+ registration.getPersonal().getEmail());
        }
        participantListResponseDto.setParticipants(participants);
        event.setInvitesSent(true);
        eventRepository.save(event);

        return ResponseEntity.ok(participantListResponseDto);
    }
}
