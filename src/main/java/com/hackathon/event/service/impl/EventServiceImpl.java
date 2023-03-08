package com.hackathon.event.service.impl;

import com.hackathon.event.dto.EventRequestDto;
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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
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
    public ResponseEntity<String> invite(Long eventId,ParticipantListRequestDto participantListRequestDto) {
        participantListRequestDto.getParticipants().sort(Comparator.comparing(p -> {
            Registration registration = registrationRepository.findById(p.getRegistrationId()).orElseThrow(() -> new EntityNotFoundException("Registration doesn't exist"));
            return registration.getScore();
        }, Comparator.reverseOrder()));

        for (ParticipantRequestDto participantRequestDto: participantListRequestDto.getParticipants()) {
            Participant participant = new Participant();
            participant.setEmail(participantRequestDto.getEmail());

            Registration registration = registrationRepository.findById(participantRequestDto.getRegistrationId()).orElseThrow(() -> new EntityNotFoundException("Registration doesn't exist"));
            participant.setRegistration(registration);
            participantRepository.save(participant);

            String emailSubject = "Invitation to event";
            String emailText = "" +
                    "Dear " + participant.getEmail() + ", \n\n" +
                    "Invited to event" +
                    "\n\n Lp, Your organiser";

      //      emailService.send(participant.getEmail(), emailSubject, emailText);
            System.out.println("Poslan mail"+ participant.getEmail());
        }
        return ResponseEntity.ok("All mails sent");
    }


    @Override
    public TeamUpResponseDto teamUp(Long eventId) {
        TeamUpResponseDto teamUpResponseDto = new TeamUpResponseDto();

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Event not found")
        );
        Integer numberOfPartitions = event.getTeams().size();
        List<Registration> allRegistrations = event.getRegistrations();

        /*
         *To partition the registrations into teams, we first sort
         *  the registrations by score in descending order.
         *  Then we loop through the registrations and add them
         *  to a team until we reach the team size. We repeat this process
         *  for all the registrations until we have formed all the teams.
         *  Finally, we print out the teams and their registrations.
        * */

        return teamUpResponseDto;
    }

    public List<Registration> sortRegistsrations(List<Registration> registrations){
        List<Registration> sortedRegistration = new ArrayList<>();



        return sortedRegistration;
    }
}
