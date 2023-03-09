package com.hackathon.event.service.impl;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.ParticipantListResponseDto;
import com.hackathon.event.dto.TeamResponseDto;
import com.hackathon.event.dto.TeamUpResponseDto;
import com.hackathon.event.dto.ParticipantListRequestDto;
import com.hackathon.event.dto.ParticipantRequestDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

        return ResponseEntity.ok(participantListResponseDto);
    }


    @Override
    public List<TeamUpResponseDto> teamUp(Long eventId) {
        List<TeamUpResponseDto> teamUpResponseDto = new ArrayList<>();

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Event not found")
        );
        List<Registration> allRegistrations = event.getRegistrations();
        List<Team> teams = event.getTeams();
        Integer numberOfTeams = event.getTeams().size();

        /*
         *  To partition the registrations into teams, we first sort
         *  the registrations by score in descending order.
         *  Then we loop through the registrations and add them
         *  to a team until we reach the team size. We repeat this process
         *  for all the registrations until we have formed all the teams.
         *  Finally, we print out the teams and their registrations.
        * */

        sortRegistsrations(allRegistrations);


        for(Integer i = 0; i < allRegistrations.size(); i++){
            Registration registration = allRegistrations.get(i);
            teams.get(i % numberOfTeams).getMembers().add(registration.getParticipant());
        }

        for(Team team : teams){
            for(Participant participant : team.getMembers()){
                System.out.println(participant.getEmail());
            }
        }

        return teamUpResponseDto;
    }

    public void sortRegistsrations(List<Registration> registrations){
        registrations.sort(Comparator.comparing(Registration::getScore));
    }
}
