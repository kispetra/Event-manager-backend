package com.hackathon.event.repository;

import com.hackathon.event.model.Experience;
import com.hackathon.event.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
