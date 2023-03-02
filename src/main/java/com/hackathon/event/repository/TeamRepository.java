package com.hackathon.event.repository;

import com.hackathon.event.model.Event;
import com.hackathon.event.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
