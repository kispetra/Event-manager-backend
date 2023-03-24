package com.hackathon.event.services;

import com.hackathon.event.dto.FlowRequestDto;
import com.hackathon.event.dto.ParticipantResponseDto;
import com.hackathon.event.mapper.ParticipantMapper;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Flow;
import com.hackathon.event.model.Participant;
import com.hackathon.event.model.Progress;
import com.hackathon.event.repository.*;
import com.hackathon.event.service.impl.ParticipantServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)

public class ParticipantServiceImplTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private ParticipantRepository participantRepository;
    @Mock
    private ProgressRepository progressRepository;
    @Mock
    private FlowRepository flowRepository;
    @Mock
    private ParticipantMapper participantMapper;
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private ParticipantServiceImpl participantService;

    @Before
    private void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProgress_returnsOk() {
        Event event = new Event();
        event.setWeeks(4);

        Participant participant = new Participant();

        FlowRequestDto flowRequestDto = new FlowRequestDto();
        flowRequestDto.setComment("TEST");
        flowRequestDto.setStatus("IN_PROGRESS");

        ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok("Saved.");

        Progress progress = new Progress();
        progress.setWeek(5);
        progress.setFlow(new ArrayList<>());
        progress.setId(5L);

        participant.setProgress(new ArrayList<>());
        participant.getProgress().add(progress);

        Flow flow = new Flow();

        Mockito.when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));
        Mockito.when(participantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(participant));
        Mockito.when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        Mockito.when(progressRepository.save(any(Progress.class))).thenReturn(progress);
        Mockito.when(flowRepository.save(any(Flow.class))).thenReturn(flow);


        ResponseEntity actualSavedProgressResponse = participantService.saveProgress(55L, 55L, 4, flowRequestDto);

        Assertions.assertEquals(expectedResponseEntity.getStatusCode(), actualSavedProgressResponse.getStatusCode());
        Assertions.assertEquals(expectedResponseEntity.getBody(), actualSavedProgressResponse.getBody());

    }

    @Test
    public void testGetAllParticipants() {
        Long eventId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        List<Participant> participants = new ArrayList<>();
        Participant participant1 = new Participant();
        participant1.setParticipantId(1L);
        participant1.setEmail("hehe@hehe.com");
        participants.add(participant1);

        Page<Participant> page = new PageImpl<>(participants, pageable, participants.size());

        Mockito.when(participantRepository.getAllParticipantsByEventId(Mockito.anyLong(), any(Pageable.class))).thenReturn(page);

        List<ParticipantResponseDto> responseDtos = new ArrayList<>();
        ParticipantResponseDto participantResponseDto1 = new ParticipantResponseDto();
        participantResponseDto1.setParticipation(true);
        responseDtos.add(participantResponseDto1);

        Mockito.when(participantMapper.toDto(any(Participant.class))).thenReturn(responseDtos.get(0));

        Page<ParticipantResponseDto> expected = new PageImpl<>(responseDtos, pageable, responseDtos.size());
        Page<ParticipantResponseDto> actual = participantService.getAllParticipants(eventId, pageable);

        Assertions.assertEquals(expected, actual);
    }
}
