package com.hackathon.event.service.impl;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.mapper.EventMapper;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Mentor;
import com.hackathon.event.model.Team;
import com.hackathon.event.repository.EventRepository;
import com.hackathon.event.repository.MentorRepository;
import com.hackathon.event.repository.TeamRepository;
import com.hackathon.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final TeamRepository teamRepository;
    private final MentorRepository mentorRepository;

    public void save(EventRequestDto eventRequestDto){
        Event event = eventMapper.toEntity(eventRequestDto);
        eventRepository.save(event);
        for (Team team : event.getTeams()){
            team.setEvent(event);
            teamRepository.save(team);
            for(Mentor mentor : team.getMentors()){
                mentor.setTeam(team);
                mentorRepository.save(mentor);
            }
        }
    }
}
