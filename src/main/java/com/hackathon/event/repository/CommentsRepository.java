package com.hackathon.event.repository;

import com.hackathon.event.model.Comments;
import com.hackathon.event.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
