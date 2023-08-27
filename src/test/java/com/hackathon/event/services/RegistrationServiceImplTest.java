package com.hackathon.event.services;

import com.hackathon.event.dto.*;
import com.hackathon.event.mapper.RegistrationMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.repository.CommentRepository;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.repository.RegistrationRepository;
import com.hackathon.event.service.impl.RegistrationServiceImpl;
import com.hackathon.event.util.ScoringEngine;
import org.apache.coyote.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RegistrationMapper registrationMapper;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Ignore
    void testSaveRegistration_returnsCreated() throws URISyntaxException {

        ScoringEngine scoringEngine = mock(ScoringEngine.class);

        UUID testUUID = UUID.randomUUID();
        ResponseEntity<String> expectedResponseEntity = ResponseEntity.created(URI.create("/event/55/registrations/" + testUUID)).body("created");

        RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto();
        registrationRequestDto.setPreferredOS("TEST OS");
        registrationRequestDto.setMotivation("TEST MOTIVATION");

        PersonalRequestDto personalRequestDto = new PersonalRequestDto();

        NameRequestDto nameRequestDto = new NameRequestDto();
        nameRequestDto.setFirst("TEST FIRST");
        nameRequestDto.setLast("TEST LAST");
        personalRequestDto.setName(nameRequestDto);

        EducationRequestDto educationRequestDto = new EducationRequestDto();
        educationRequestDto.setFaculty("TEST FACULTY");
        educationRequestDto.setYear(1997);

        personalRequestDto.setEducation(educationRequestDto);

        registrationRequestDto.setPersonal(personalRequestDto);

        ExperienceRequestDto experienceRequestDto = new ExperienceRequestDto();
        experienceRequestDto.setRepositoryUrl("https://github.com/milardich");
        experienceRequestDto.setSkills(new ArrayList<>());
        experienceRequestDto.setSummary("TEST SUMMARY");
        experienceRequestDto.setYears(5);

        registrationRequestDto.setExperience(experienceRequestDto);

        Event event = new Event();
        event.setEventId(55L);
        event.setInvitesSent(false);
        event.setRegistrations(new ArrayList<>());
        event.setName("TEST EVENT");
        event.setConfirmationNotAfter(new Date());
        event.setRegistrationsNotBefore(new Date());
        event.setRegistrationsNotAfter(new Date(2099, 5, 1));
        event.setMaxParticipants(5);
        event.setStartDate(new Date());
        event.setWeeks(5);

        Registration registration = new Registration();
        registration.setRegistrationUUID(UUID.randomUUID());

        Personal personal = new Personal();

        Name name = new Name();
        name.setFirstName("TEST");
        name.setLastName("test");

        personal.setName(name);

        Education education = new Education();
        education.setYear(5);
        education.setEducationId(5L);
        education.setFaculty("TEST");
        personal.setEducation(education);

        registration.setPersonal(personal);

        Experience experience = new Experience();
        experience.setSkills(new ArrayList<>());
        experience.setYears(5);
        experience.setSummary("qawe");
        experience.setExperienceId(5L);

        registration.setExperience(experience);
        registration.setRegistrationUUID(UUID.fromString(testUUID.toString()));

        when(scoringEngine.CalculateScore(Mockito.any(RegistrationRequestDto.class))).thenReturn(156);
        when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));
        when(registrationMapper.toEntity(Mockito.any(RegistrationRequestDto.class), Mockito.any(Event.class))).thenReturn(registration);
        when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(registration);

        ResponseEntity actualSavedRegistrationResponse = registrationService.save(55L, registrationRequestDto);

        assertEquals(expectedResponseEntity.getStatusCode(), actualSavedRegistrationResponse.getStatusCode());
    }

    @Test
    public void testDeleteById_returnsOk() {
        Event event = new Event();
        Registration registration = new Registration();
        UUID registrationUUID = UUID.randomUUID();
        registration.setRegistrationUUID(registrationUUID);
        event.setRegistrations(new ArrayList<>());
        event.getRegistrations().add(registration);

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Deleted.");

        Mockito.when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));
        Mockito.when(registrationRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(registration));
        Mockito.doNothing().when(registrationRepository).delete(registration);

        ResponseEntity actualResponse = registrationService.deleteById(55L, registrationUUID.toString());

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}
