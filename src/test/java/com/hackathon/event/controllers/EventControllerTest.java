package com.hackathon.event.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.event.controller.EventController;
import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.TeamRequestDto;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.ExpectedCount.times;

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
}
