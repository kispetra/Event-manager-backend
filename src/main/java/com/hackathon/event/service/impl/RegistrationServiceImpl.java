package com.hackathon.event.service.impl;

import com.hackathon.event.dto.ConfirmationRequestDto;
import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.dto.RegistrationResponseDto;
import com.hackathon.event.dto.CommentRequestDto;
import com.hackathon.event.mapper.RegistrationMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.RegistrationService;
import com.hackathon.event.util.ScoringEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final RegistrationMapper registrationMapper;
    private final SkillRepository skillRepository;
    private final CommentRepository commentRepository;
    private final ParticipantRepository participantRepository;


    @Override
    public ResponseEntity<String> save(Long eventId, RegistrationRequestDto registrationRequestDto) {
        ScoringEngine scoringEngine = new ScoringEngine();
        Integer score = scoringEngine.CalculateScore(registrationRequestDto);

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found."));
        Date date= new Date();

        if(date.before(event.getRegistrationsNotBefore()) || date.after(event.getRegistrationsNotAfter())){
            return  ResponseEntity.status(405).body("Event is not accepting registrations.");
        }

        Registration registration = registrationMapper.toEntity(registrationRequestDto, event);
        URI locationUri= ServletUriComponentsBuilder
                .fromCurrentRequest().path("/event/{eventId}/registrations").buildAndExpand(event.getEventId()).toUri();

        Name name = registration.getPersonal().getName();
        name.setPersonal(registration.getPersonal());
        registration.getPersonal().setName(name);

        Education education = registration.getPersonal().getEducation();
        education.setPersonal(registration.getPersonal());
        registration.getPersonal().setEducation(education);

        Personal personal = registration.getPersonal();
        personal.setRegistration(registration);
        registration.setPersonal(personal);

        Experience experience = registration.getExperience();
        experience.setRegistration(registration);
        registration.setExperience(experience);
        registration.setScore(score);
        registration.setRegistrationUUID(UUID.randomUUID());
        registrationRepository.save(registration);

        for (Skill skill : registration.getExperience().getSkills()) {
            skill.setExperience(experience);
            skillRepository.save(skill);
        }

        String emailSubject = "Registration filled";
        String emailText = "" +
                "Dear " + registration.getPersonal().getName().getFirstName() + ", \n\n" +
                "Registration done right" +
                "\n\n Lp, Your organiser";

        //      emailService.send(participant.getEmail(), emailSubject, emailText);
        System.out.println("Poslan mail" +" " + registration.getPersonal().getName().getFirstName() );

        return ResponseEntity.created(locationUri).body("created");

    }

    @Override
    public ResponseEntity<String> deleteById(Long eventId, Long registrationId) {

        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        Registration registration = registrationRepository.findById(registrationId).orElseThrow
                (() -> new EntityNotFoundException("Registration not found"));

        event.getRegistrations().remove(registration);
        registrationRepository.delete(registration);

        return ResponseEntity.ok("Deleted.");
    }

    @Override
    public ResponseEntity<String> score(Long eventId, Long registrationId, CommentRequestDto scoreRequestDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new EntityNotFoundException("Event doesn't exist."));
        Registration registration = registrationRepository.findById(registrationId).orElseThrow(() -> new EntityNotFoundException("Registration doesn't exist"));
        if(scoreRequestDto.getScore().isEmpty() || scoreRequestDto.getComment().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        if (scoreRequestDto.getScore().charAt(0) == '+') {
            String valueText = scoreRequestDto.getScore().substring(1);
            Integer updatedScore = registration.getScore();
            updatedScore += Integer.parseInt(valueText);
            registration.setScore(updatedScore);
            registrationRepository.save(registration);
        } else if (scoreRequestDto.getScore().charAt(0) == '-') {
            String valueText = scoreRequestDto.getScore().substring(1);
            Integer updatedScore = registration.getScore();
            updatedScore -= Integer.parseInt(valueText);
            registration.setScore(updatedScore);
            registrationRepository.save(registration);
        } else {
            return ResponseEntity.badRequest().body("Bad format");
        }

        Comment comment = new Comment();
        comment.setScore(scoreRequestDto.getScore());
        comment.setComment(scoreRequestDto.getComment());
        comment.setRegistration(registration);
        commentRepository.save(comment);

        return ResponseEntity.ok("Saved");
    }
    @Override
    public RegistrationResponseDto fetchById(@PathVariable Long eventId, @PathVariable Long registrationId){
        Event event = eventRepository.findById(eventId).orElseThrow
                     (() -> new EntityNotFoundException("Event not found"));
        Registration registration = registrationRepository.findById(registrationId).orElseThrow
                     (() -> new EntityNotFoundException("Registration not found"));

        RegistrationResponseDto registrationResponseDto= registrationMapper.toDto(registration);

        return registrationResponseDto;
    }

    @Override
    public Page<RegistrationResponseDto> getAllRegistrations(Long eventId, Pageable pageable) {
        return registrationRepository.getAllRegistrationsByEventId(eventId, pageable).map(registrationMapper::toDto);
    }
    @Override
    public ResponseEntity<String> patchById(Long eventId, Long registrationId, ConfirmationRequestDto confirmationRequestDto){
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        Registration registration = registrationRepository.findById(registrationId).orElseThrow
                (() -> new EntityNotFoundException("Registration not found"));

        if(confirmationRequestDto.getKickoff().equals(null)|| confirmationRequestDto.getParticipation().equals(null)
            || confirmationRequestDto.getTshirt().isEmpty() || confirmationRequestDto.getGitlab().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        Date date= new Date();
        if(date.after(event.getConfirmationNotAfter())){
            return ResponseEntity.status(405).body("Method not allowed.");
        }
        if( registration.getParticipation()!=null && registration.getParticipation()){
            return ResponseEntity.status(405).body("Already accepted.");
        }
        if(participantRepository.existsById(registrationId)){
            return ResponseEntity.status(405).body("Not invited.");
        }
        registration.setParticipation(confirmationRequestDto.getParticipation());
        registration.setKickoff(confirmationRequestDto.getKickoff());
        registration.setTshirt(confirmationRequestDto.getTshirt());
        registration.setGitlab(confirmationRequestDto.getGitlab());

        registrationRepository.save(registration);

        String emailSubject = "Participation confirmed";
        String emailText = "" +
                "Dear " + registration.getPersonal().getName().getFirstName() + ", \n\n" +
                "Confirmation confirmed" +
                "\n\n Lp, Your organiser";

        //      emailService.send(participant.getEmail(), emailSubject, emailText);
        System.out.println("Potvrdio" +" " + registration.getPersonal().getName().getFirstName() );

        return ResponseEntity.ok("Done.");
    }

}
