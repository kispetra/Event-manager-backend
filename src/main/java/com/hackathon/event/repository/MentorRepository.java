package com.hackathon.event.repository;

import com.hackathon.event.model.Event;
import com.hackathon.event.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor,Long> {
}
