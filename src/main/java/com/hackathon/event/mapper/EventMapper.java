package com.hackathon.event.mapper;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.EventResponseDto;
import com.hackathon.event.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventMapper {
    public Event toEntity(EventRequestDto eventRequestDto){
        Event event= new Event();

        event.setName(eventRequestDto.getName());
        event.setDescription(eventRequestDto.getDescription());
        event.setMaxParticipants(eventRequestDto.getMaxParticipants());
        event.setRegistrationsNotBefore(eventRequestDto.getRegistrationsNotBefore());
        event.setRegistrationsNotAfter(eventRequestDto.getRegistrationsNotAfter());
        event.setConfirmationNotAfter(eventRequestDto.getConfirmationNotAfter());
        event.setStartDate(eventRequestDto.getStartDate());
        event.setWeeks(eventRequestDto.getWeeks());
        event.setInvitesSent(false);

        return event;
    }
    public EventResponseDto toDto(Event event){
        EventResponseDto eventResponseDto=new EventResponseDto();

        eventResponseDto.setName(event.getName());
        eventResponseDto.setDescription(event.getDescription());
        eventResponseDto.setMaxParticipants(event.getMaxParticipants());
        eventResponseDto.setRegistrationsNotBefore(event.getRegistrationsNotBefore());
        eventResponseDto.setRegistrationsNotAfter(event.getRegistrationsNotAfter());
        eventResponseDto.setConfirmationNotAfter(event.getConfirmationNotAfter());
        eventResponseDto.setStartDate(event.getStartDate());
        eventResponseDto.setWeeks(event.getWeeks());

        return eventResponseDto;
    }
}
