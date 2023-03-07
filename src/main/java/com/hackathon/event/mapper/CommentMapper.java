package com.hackathon.event.mapper;

import com.hackathon.event.dto.CommentResponseDto;
import com.hackathon.event.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentMapper {

    public CommentResponseDto toDto(Comment comment)
    {
        CommentResponseDto commentResponseDto= new CommentResponseDto();
        commentResponseDto.setComment(comment.getComment());
        commentResponseDto.setScore(comment.getScore());

        return commentResponseDto;
    }

}
