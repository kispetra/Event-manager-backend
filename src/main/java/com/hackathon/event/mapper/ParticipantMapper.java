package com.hackathon.event.mapper;

import com.hackathon.event.dto.*;
import com.hackathon.event.model.Comment;
import com.hackathon.event.model.Participant;
import com.hackathon.event.model.Progress;
import com.hackathon.event.model.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantMapper {
    private final CommentMapper commentMapper;
    private final ProgressMapper progressMapper;
    private final SkillMapper skillMapper;
    public ParticipantResponseDto toDto(Participant participant){
        ParticipantResponseDto participantResponseDto= new ParticipantResponseDto();
        participantResponseDto.setParticipantId(participant.getParticipantId());
        PersonalResponseDto personalResponseDto=new PersonalResponseDto();

        NameResponseDto nameResponseDto= new NameResponseDto();
        nameResponseDto.setFirst(participant.getRegistration().getPersonal().getName().getFirstName());
        nameResponseDto.setLast(participant.getRegistration().getPersonal().getName().getLastName());
        personalResponseDto.setName(nameResponseDto);
        personalResponseDto.setEmail(participant.getRegistration().getPersonal().getEmail());
        personalResponseDto.setPhone(participant.getRegistration().getPersonal().getPhone());
        EducationResponseDto educationResponseDto = new EducationResponseDto();
        educationResponseDto.setYear(participant.getRegistration().getPersonal().getEducation().getYear());
        educationResponseDto.setFaculty(participant.getRegistration().getPersonal().getEducation().getFaculty());
        personalResponseDto.setEducation(educationResponseDto);
        personalResponseDto.setSummary(participant.getRegistration().getPersonal().getSummary());
        participantResponseDto.setPersonal(personalResponseDto);

        ExperienceResponseDto experienceResponseDto= new ExperienceResponseDto();
        experienceResponseDto.setYears(participant.getRegistration().getExperience().getYears());
        List<SkillResponseDto> skills = new ArrayList<>();
        for(Skill skill : participant.getRegistration().getExperience().getSkills()){
            SkillResponseDto skillToSave = skillMapper.toDto(skill);
            skills.add(skillToSave);
        }
        experienceResponseDto.setSkills(skills);
        experienceResponseDto.setSummary(participant.getRegistration().getExperience().getSummary());
        participantResponseDto.setExperience(experienceResponseDto);
        participantResponseDto.setScore(participant.getRegistration().getScore());

        List<CommentResponseDto> comments= new ArrayList<>();
        for(Comment comment: participant.getRegistration().getComment()){
            CommentResponseDto commentResponseDto= commentMapper.toDto(comment);
            comments.add(commentResponseDto);
        }
        participantResponseDto.setComments(comments);
        participantResponseDto.setParticipation(participant.getRegistration().getParticipation());


        List<ProgressResponseDto> progresses= new ArrayList<>();
        for(Progress progress: participant.getRegistration().getParticipant().getProgress()){
            ProgressResponseDto progressResponseDto= progressMapper.toDto(progress);
            progresses.add(progressResponseDto);
        }
        participantResponseDto.setProgress(progresses);

        return participantResponseDto;
    }
}
