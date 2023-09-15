package com.hackathon.event.mapper;

import com.hackathon.event.dto.EventRequestDto;
import com.hackathon.event.dto.EventResponseDto;
import com.hackathon.event.dto.SkillRequestDto;
import com.hackathon.event.dto.SkillResponseDto;
import com.hackathon.event.model.AppUser;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventMapper {
     private final SkillMapper skillMapper;
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
        List<Skill> skills=new ArrayList<>();
        for (SkillRequestDto skill: eventRequestDto.getSkills()) {
            Skill skill1 = skillMapper.toEntity(skill);
            skill1.setEvent(event);
            skills.add(skill1);
        }
        event.setSkills(skills);
        event.setInvitesSent(false);

        return event;
    }
    public Event toEntityUser(EventRequestDto eventRequestDto, AppUser appUser){
        Event event= new Event();

        event.setAppUser(appUser);
        event.setName(eventRequestDto.getName());
        event.setDescription(eventRequestDto.getDescription());
        event.setMaxParticipants(eventRequestDto.getMaxParticipants());
        event.setRegistrationsNotBefore(eventRequestDto.getRegistrationsNotBefore());
        event.setRegistrationsNotAfter(eventRequestDto.getRegistrationsNotAfter());
        event.setConfirmationNotAfter(eventRequestDto.getConfirmationNotAfter());
        event.setStartDate(eventRequestDto.getStartDate());
        event.setWeeks(eventRequestDto.getWeeks());
        List<Skill> skills=new ArrayList<>();
        for (SkillRequestDto skill: eventRequestDto.getSkills()) {
            Skill skill1 = skillMapper.toEntity(skill);
            skill1.setEvent(event);
            skills.add(skill1);
        }
        event.setSkills(skills);
        event.setInvitesSent(false);

        return event;
    }
    public EventResponseDto toDto(Event event){
        EventResponseDto eventResponseDto=new EventResponseDto();

        eventResponseDto.setEventId(event.getEventId());
        eventResponseDto.setName(event.getName());
        eventResponseDto.setDescription(event.getDescription());
        eventResponseDto.setMaxParticipants(event.getMaxParticipants());
        eventResponseDto.setRegistrationsNotBefore(event.getRegistrationsNotBefore());
        eventResponseDto.setRegistrationsNotAfter(event.getRegistrationsNotAfter());
        eventResponseDto.setConfirmationNotAfter(event.getConfirmationNotAfter());
        eventResponseDto.setStartDate(event.getStartDate());
        eventResponseDto.setWeeks(event.getWeeks());
        List<SkillResponseDto> skills=new ArrayList<>();
        for (Skill skill: event.getSkills()) {
            skills.add(skillMapper.toDto(skill));
        }
        eventResponseDto.setSkills(skills);
        return eventResponseDto;
    }
}
