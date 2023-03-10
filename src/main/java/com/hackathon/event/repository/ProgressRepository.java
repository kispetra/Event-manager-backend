package com.hackathon.event.repository;

import com.hackathon.event.model.Participant;
import com.hackathon.event.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
}
