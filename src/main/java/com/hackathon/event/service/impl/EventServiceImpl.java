package com.hackathon.event.service.impl;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.ParticipantListResponseDto;
import com.hackathon.event.dto.TeamResponseDto;
import com.hackathon.event.dto.TeamUpResponseDto;
import com.hackathon.event.mapper.EventMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.EmailService;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Mentor;
import com.hackathon.event.model.Registration;
import com.hackathon.event.model.Team;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.repository.MentorRepository;
import com.hackathon.event.repository.TeamRepository;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
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
    private final TeamRepository teamRepository;
    private final MentorRepository mentorRepository;
    private final RegistrationRepository registrationRepository;
    private final ParticipantRepository participantRepository;
    private final EmailService emailService;

    public ResponseEntity<String> save(EventRequestDto eventRequestDto){

        Event event = eventMapper.toEntity(eventRequestDto);
        URI locationUri= ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{eventId}").buildAndExpand(event.getEventId()).toUri();
        List<Event> events = eventRepository.findAll();
        for(Event e: events) {
            if (e.getName().equals(event)) {
                return ResponseEntity.badRequest().body("An event with the same name already exists or multiple teams having the same name");
            }
        }
        eventRepository.save(event);
        for (Team team : event.getTeams()){
            team.setEvent(event);
            teamRepository.save(team);
            for(Mentor mentor : team.getMentors()){
                mentor.setTeam(team);
                mentorRepository.save(mentor);
            }
        }
        return ResponseEntity.created(locationUri).body("created");
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


    @Override
    public TeamUpResponseDto teamUp(Long eventId) {
        TeamUpResponseDto response = new TeamUpResponseDto();

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Event not found")
        );
        List<Registration> allRegistrations = event.getRegistrations();
        List<Team> teams = event.getTeams();
        Integer numberOfTeams = event.getTeams().size();

        sortRegistrations(allRegistrations);

        for(Integer i = 0; i < allRegistrations.size(); i++){
            if(allRegistrations.get(i).getParticipation()) {
                Integer teamIndex = i % numberOfTeams;
                Registration registration = allRegistrations.get(i);
                teams.get(teamIndex).getMembers().add(registration.getParticipant());
                registration.getParticipant().setTeam(teams.get(teamIndex));
            }
        }

        List<TeamResponseDto> teamResponses = new ArrayList<>();

        for(Team team : teams){
            List<String> teamMembers = new ArrayList<>();
            TeamResponseDto teamResponse = new TeamResponseDto();

            for(Participant participant : team.getMembers()){
                participantRepository.save(participant);
                if(!teamMembers.contains(participant.getEmail())){
                    teamMembers.add(participant.getEmail());
                }
            }
            teamResponse.setName(team.getName());
            teamResponse.setMembers(teamMembers);
            teamResponses.add(teamResponse);
        }

        response.setTeams(teamResponses);

        return response;
    }

    public void sortRegistrations(List<Registration> registrations){
        registrations.sort(Comparator.comparing(Registration::getScore));
    }
}
