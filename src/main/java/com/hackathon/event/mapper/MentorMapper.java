package com.hackathon.event.mapper;

import com.hackathon.event.model.Mentor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MentorMapper {
    public Mentor toEntity(String mentorEmail){
        Mentor mentor=new Mentor();
        mentor.setEmail(mentorEmail);

        return mentor;
    }
}
