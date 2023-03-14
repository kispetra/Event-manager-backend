package com.hackathon.event.mapper;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.TeamRequestDto;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Team;
import com.hackathon.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventMapper {
    private final TeamMapper teamMapper;
    public Event toEntity(EventRequestDto eventRequestDto){
        Event event= new Event();

        event.setName(eventRequestDto.getName());
        event.setMaxParticipants(eventRequestDto.getMaxParticipants());

        List<Team> teams = new ArrayList<>();

        for (TeamRequestDto teamRequestDto : eventRequestDto.getTeams()) {
            Team team = teamMapper.toEntity(teamRequestDto);
            teams.add(team);
        }

        event.setTeams(teams);
        event.setRegistrationsNotBefore(eventRequestDto.getRegistrationsNotBefore());
        event.setRegistrationsNotAfter(eventRequestDto.getRegistrationsNotAfter());
        event.setConfirmationNotAfter(eventRequestDto.getConfirmationNotAfter());
        event.setStartDate(eventRequestDto.getStartDate());
        event.setWeeks(eventRequestDto.getWeeks());

        return event;
    }
}
