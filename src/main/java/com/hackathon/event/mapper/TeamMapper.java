package com.hackathon.event.mapper;

import com.hackathon.event.dto.TeamRequestDto;
import com.hackathon.event.model.Mentor;
import com.hackathon.event.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class TeamMapper {
    private final MentorMapper mentorMapper;

    public Team toEntity(TeamRequestDto teamRequestDto){
        Team team= new Team();

        team.setName(teamRequestDto.getName());
        List<Mentor> mentors= new ArrayList<>();

        List<String> teamMentorsFromRequest = teamRequestDto.getMentors();
        if(teamMentorsFromRequest != null){
            for(String mentorEmail: teamMentorsFromRequest) {
                Mentor mentor = mentorMapper.toEntity(mentorEmail);
                mentors.add(mentor);
            }
        }
        team.setMentors(mentors);

        return team;

    }

}
