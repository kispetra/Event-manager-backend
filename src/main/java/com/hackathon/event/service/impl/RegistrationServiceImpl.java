package com.hackathon.event.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.event.dto.*;
import com.hackathon.event.mapper.RegistrationMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.EmailService;
import com.hackathon.event.service.RegistrationService;
import com.hackathon.event.util.ScoringEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final EmailService emailService;
    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final RegistrationMapper registrationMapper;
    private final CommentRepository commentRepository;
    private final SkillRepository skillRepository;

    @Override @Transactional
    public ResponseEntity<String> save(Long eventId, RegistrationRequestDto registrationRequestDto) {
        ScoringEngine scoringEngine = new ScoringEngine(skillRepository);
        Integer score = scoringEngine.CalculateScore(registrationRequestDto);

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found."));
        Date date= new Date();

        if(date.before(event.getRegistrationsNotBefore()) || date.after(event.getRegistrationsNotAfter())){
            return  ResponseEntity.status(405).body("Event is not accepting registrations.");
        }

        Registration registration = registrationMapper.toEntity(registrationRequestDto, event);


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

        List<Skill> skills = new ArrayList<>();
        List<SkillNameRequestDto> skillNameRequestDtoList=registrationRequestDto.getExperience().getSkills();
        for (SkillNameRequestDto skillNameRequestDto : skillNameRequestDtoList) {
            Skill skill=skillRepository.findFirstByName(skillNameRequestDto.getName());
            if (skill == null) {
                return ResponseEntity.badRequest().body("Skill not found: " + skillNameRequestDto.getName());
            }

            Skill newS = new Skill();
            newS.setName(skill.getName());
            newS.setPoints(skill.getPoints());
            newS.setExperience(experience);
            newS.setEvent(event);
            skills.add(newS);
        }
        experience.setSkills(skills);

        registration.setExperience(experience);
        registration.setScore(score);

        registrationRepository.save(registration);
        URI locationUri= ServletUriComponentsBuilder
                .fromCurrentRequest().path("/event/{eventId}/registrations").buildAndExpand(event.getEventId()).toUri();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm"); // Postavite željeni format

        String formattedDate = dateFormat.format(event.getConfirmationNotAfter());

        String emailSubject = "Prijava je uspješno poslana";
        String emailText = "" +
                "Pozdrav " + registration.getPersonal().getName().getFirstName() + ", \n\n" +
                "uspješno ste se prijavili na događaj pod nazivom: " + event.getName() + "."
                +
                "\n Potvrdu o prihvaćanju na događaj dobiti ćete najkasnije "+ formattedDate+"h."+
                "\n\n Lijep pozdrav,\n " + event.getName();

        emailService.send(registration.getPersonal().getEmail(), emailSubject, emailText);
        System.out.println("Poslan mail" +" " + registration.getPersonal().getName().getFirstName() );

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "created");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse;
        try {
            jsonResponse = objectMapper.writeValueAsString(responseMessage);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body("Error creating JSON response.");
        }

        return ResponseEntity.ok(jsonResponse);

    }
    public List<Skill> getSkillsByEventId(Long eventId) {

        return skillRepository.findByEventEventId(eventId);
    }
    @Override
    public ResponseEntity<String> deleteById(Long eventId, Long registrationId) {

        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        Registration registration = registrationRepository.findById(registrationId).orElseThrow
                (() -> new EntityNotFoundException("Registration not found"));

        event.getRegistrations().remove(registration);
        registrationRepository.delete(registration);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> score(Long eventId, Long registrationId, CommentRequestDto scoreRequestDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new EntityNotFoundException("Event doesn't exist."));
        Registration registration = registrationRepository.findById(registrationId).orElseThrow(() -> new EntityNotFoundException("Registration doesn't exist"));
        if (scoreRequestDto.getScore() == null || scoreRequestDto.getComment() == null) {
            return ResponseEntity.badRequest().body("Score and comment cannot be null");
        }
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
            return ResponseEntity.noContent().build();
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
    public List<RegistrationResponseDto> getAllUserRegistrations( Long eventId){
        List<Registration> registrations=registrationRepository.getAllUserRegistrationsByEventId(eventId);
        List<RegistrationResponseDto> registrationResponseDtos= new ArrayList<>();
        for (Registration registration: registrations) {
            RegistrationResponseDto dto =registrationMapper.toDto(registration);
            registrationResponseDtos.add(dto);
        }
        return  registrationResponseDtos;
    }
    @Override
    public ResponseEntity<String> patchById(Long eventId, Long registrationId, ConfirmationRequestDto confirmationRequestDto){
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new EntityNotFoundException("Event not found"));
        Registration registration = registrationRepository.findById(registrationId).orElseThrow
                (() -> new EntityNotFoundException("Registration not found"));

        if( confirmationRequestDto.getParticipation().equals(null)){
            return ResponseEntity.noContent().build();
        }
        Date date= new Date();
        if(date.after(event.getConfirmationNotAfter())){
            return ResponseEntity.status(405).body("Method not allowed.");
        }
        if( registration.getParticipation()!=null && registration.getParticipation()){
            return ResponseEntity.status(405).body("Already accepted.");
        }

        registration.setParticipation(confirmationRequestDto.getParticipation());
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
