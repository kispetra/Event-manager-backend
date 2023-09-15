package com.hackathon.event.controller;

import com.hackathon.event.config.JwtAuthFilter;
import com.hackathon.event.config.UserAuthProvider;
import com.hackathon.event.dto.*;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Skill;
import com.hackathon.event.repository.SkillRepository;
import com.hackathon.event.repository.UserRepo;
import com.hackathon.event.service.EventService;
import com.hackathon.event.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class UserController {


 private final UserAuthProvider userAuthProvider;
    @GetMapping("/user")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDto) {
            UserDto user = (UserDto) authentication.getPrincipal();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //getAllEvents of that appuser
    private final EventService eventService;
    private final RegistrationService registrationService;
    private final SkillRepository skillRepository;

    @GetMapping("/user/{userId}/event/all")
    public List<EventResponseDto> getAllEventsOfUser(@PathVariable Long userId){
        return eventService.getAllEventsOfUser(userId);
    }
    //create event
    @PostMapping("/user/{userId}/event")
    public ResponseEntity<String> saveEventOfUser(@PathVariable Long userId, @RequestBody EventRequestDto eventRequestDto){
        return eventService.saveEventOfUser(userId, eventRequestDto);
    }
    //get event by id
    @GetMapping("/event/{eventId}")
    public EventResponseDto getEventByIdOfUser( @PathVariable Long eventId){
        return eventService.getEventByIdOfUser( eventId);
    }
    //edit event by id
    @PutMapping("/event/{eventId}")
    public ResponseEntity<String> updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateRequestDto eventUpdateRequest) {
        return eventService.updateEvent(eventId, eventUpdateRequest);
    }

    //delete event by id
    @DeleteMapping("/event/{eventId}")
    public ResponseEntity<String> deleteById( @PathVariable Long eventId){
        return eventService.deleteEventByIdOfUser(eventId);
    }

    //lista registracija
    @GetMapping("/event/{eventId}/user-registrations")
    public List<RegistrationResponseDto> getAllRegistrations(@PathVariable Long eventId){
        return registrationService.getAllUserRegistrations(eventId);
    }

    //registracija po id-u
    @GetMapping("/event/{eventId}/registrations/{registrationId}")
    public RegistrationResponseDto fetchById(@PathVariable Long eventId, @PathVariable Long registrationId){
        return registrationService.fetchById(eventId, registrationId);
    }

    @GetMapping("/event/{eventId}/skills")
    public List<Skill>  getSkill(@PathVariable Long eventId){
        List<Skill> eventSkills = registrationService.getSkillsByEventId(eventId);
        return eventSkills;
    }

}
