package com.hackathon.event.services;

import com.hackathon.event.dto.*;
import com.hackathon.event.mapper.RegistrationMapper;
import com.hackathon.event.model.*;
import com.hackathon.event.repository.CommentRepository;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.repository.RegistrationRepository;
import com.hackathon.event.service.impl.RegistrationServiceImpl;
import com.hackathon.event.util.ScoringEngine;
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

import java.net.URI;
import java.net.URISyntaxException;
i
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    }
}
