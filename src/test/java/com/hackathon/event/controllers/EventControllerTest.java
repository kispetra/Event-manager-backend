package com.hackathon.event.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.event.controller.EventController;
import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.ParticipantListResponseDto;
import com.hackathon.event.dto.ParticipantResponseDto;
import com.hackathon.event.model.Event;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventServiceImpl eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private EventController eventController;
    @Autowired
    private EventRepository eventRepository;

    @Test
    void testSaveEvent_returns201created() {
        // prepare test data
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setName("Test Event");
        eventRequestDto.setTeams(new ArrayList<>());

        URI locationUri= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{eventId}")
                .buildAndExpand(34L)
                .toUri();

        // mock the service method
        Mockito.when(eventService.save(eventRequestDto)).thenReturn(ResponseEntity.created(locationUri).build());

        // call the controller method
        ResponseEntity<String> responseEntity = eventController.save(eventRequestDto);

        // assert the response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testInvite_returns200ok() throws Exception {

        ParticipantListResponseDto participantListResponseDto = new ParticipantListResponseDto();
        ResponseEntity response = new ResponseEntity<>(participantListResponseDto, HttpStatus.OK);

        Mockito.when(eventService.invite(Mockito.anyLong()))
                .thenReturn(response);

        Event event = eventRepository.findById(30L).orElseThrow(() -> new EntityNotFoundException("Event with id 30 does not exist"));
        event.setInvitesSent(false);
        eventRepository.save(event);

        mockMvc.perform(MockMvcRequestBuilders.put("/event/30/invite")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().isOk()));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
