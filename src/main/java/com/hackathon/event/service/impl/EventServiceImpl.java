package com.hackathon.event.service.impl;

import com.hackathon.event.dto.*;
import com.hackathon.event.mapper.EventMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.EmailService;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Registration;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final RegistrationRepository registrationRepository;
    private final ParticipantRepository participantRepository;
    private final EmailService emailService;
    private final UserRepo userRepo;


    public Page<EventResponseDto> getAll(String name, int page, int size) {
        Page<Event> events = eventRepository.findByNameContaining(name, PageRequest.of(page, size));
        List<EventResponseDto> eventDtos = new ArrayList<>();

        for (Event event : events) {
            EventResponseDto eventDto = eventMapper.toDto(event);
            eventDtos.add(eventDto);
        }

        return new PageImpl<>(eventDtos, events.getPageable(), events.getTotalElements());
    }
    public List<EventResponseDto> getAllEventsOfUser(Long userId){
        AppUser appUser= userRepo.findById(userId).orElseThrow
                (() -> new EntityNotFoundException("User not found"));;
        List<Event> events = eventRepository.findByUserId(userId);


        List<EventResponseDto> eventDtos = events.stream()
                .map(eventMapper::toDto) // Koristimo mapiranje putem stream-a
                .collect(Collectors.toList());
        return eventDtos;
    }
    public ResponseEntity<String> saveEventOfUser(Long userId, EventRequestDto eventRequestDto){
        AppUser appUser = userRepo.findById(userId).orElseThrow
                (() -> new EntityNotFoundException("User not found"));

        Event event = eventMapper.toEntityUser(eventRequestDto, appUser);

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
    public EventResponseDto getEventByIdOfUser(@PathVariable Long eventId){

        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));

        return eventMapper.toDto(event);
    }
    public ResponseEntity<String>  deleteEventByIdOfUser(Long eventId){
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));

        eventRepository.delete(event);

        return ResponseEntity.ok().build();
    }
    public ResponseEntity<String> updateEvent(Long eventId, EventUpdateRequestDto eventUpdateRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        event.setName(eventUpdateRequest.getName());
        event.setDescription(eventUpdateRequest.getDescription());
        event.setMaxParticipants(eventUpdateRequest.getMaxParticipants());

        // Pretvorba datuma iz stringa u tip Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date registrationsNotBefore;
        Date registrationsNotAfter;
        Date confirmationNotAfter;
        Date startDate;
        try {
            registrationsNotBefore = dateFormat.parse(eventUpdateRequest.getRegistrationsNotBefore());
            registrationsNotAfter = dateFormat.parse(eventUpdateRequest.getRegistrationsNotAfter());
            confirmationNotAfter= dateFormat.parse(eventUpdateRequest.getConfirmationNotAfter());
            startDate=dateFormat.parse(eventUpdateRequest.getStartDate());
        } catch (ParseException e) {
            // Pogreška u parsiranju datuma
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Neispravan format datuma.");
        }

        event.setRegistrationsNotBefore(registrationsNotBefore);
        event.setRegistrationsNotAfter(registrationsNotAfter);
        event.setConfirmationNotAfter(confirmationNotAfter);
        event.setStartDate(startDate);
        event.setWeeks(eventUpdateRequest.getWeeks());

        eventRepository.save(event);
        return ResponseEntity.ok().build();
    }
    public EventResponseDto getById(Long eventId){
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        EventResponseDto eventResponseDto= eventMapper.toDto(event);

        return eventResponseDto;
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
            registration.setParticipation(true);
            Participant participant = new Participant();
            participant.setEmail(registration.getPersonal().getEmail());
            participant.setRegistration(registration);
            participantRepository.save(participant);

           participants.add(participant);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy."); // Postavite željeni format

            String formattedDate = dateFormat.format(event.getStartDate());

            String emailSubject = "Pozivnica za " + event.getName();
            String emailText = "" +
                    "Pozdrav " + registration.getPersonal().getName().getFirstName()+ ", \n\n" +
                    " izabrani ste kao kandidat za naše događaj pod nazivom: " + event.getName() + ". "
                    + "Događaj počinje "+ formattedDate + " u 12:00 h. \nAdresa održavanja događaja je Ulica braće Radića 121, Osijek. "+
                    "\nVIDIMO SE !"+
                    "\n\n Lijep pozdrav,\n" + event.getName();

             emailService.send(participant.getEmail(), emailSubject, emailText);
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
