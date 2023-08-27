package com.hackathon.event.service.impl;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.EventResponseDto;
import com.hackathon.event.dto.ParticipantListResponseDto;
import com.hackathon.event.dto.ParticipantResponseDto;
import com.hackathon.event.mapper.EventMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.EmailService;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Registration;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final RegistrationRepository registrationRepository;
    private final ParticipantRepository participantRepository;
    private final EmailService emailService;


    public List<EventResponseDto> getAll() {
        List<Event> events = eventRepository.findAll();
        List<EventResponseDto> dto=new ArrayList<>();
        for (Event event: events
             ) {
            EventResponseDto eventDto=eventMapper.toDto(event);
            dto.add(eventDto);
        }
        return dto;
    }

    public ResponseEntity<String> save(EventRequestDto eventRequestDto){

        Event event = eventMapper.toEntity(eventRequestDto);
        List<Event> events = eventRepository.findAll();

        for(Event e: events) {
            if (e.getName().equals(event.getName())) {
                return ResponseEntity.badRequest().body("An event with the same name already exists.");
            }
        }

        eventRepository.save(event);
        URI locationUri= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{eventId}")
                .buildAndExpand(event.getEventId())
                .toUri();

        return ResponseEntity.created(locationUri).build();
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

        Integer count = 0;
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
            count++;
            if(count>=event.getMaxParticipants())break;
        }
        participantListResponseDto.setParticipants(participants);
        event.setInvitesSent(true);
        eventRepository.save(event);

        return ResponseEntity.ok(participantListResponseDto);
    }

    public void sortRegistrations(List<Registration> registrations){
        registrations.sort(Comparator.comparing(Registration::getScore));
    }
}
