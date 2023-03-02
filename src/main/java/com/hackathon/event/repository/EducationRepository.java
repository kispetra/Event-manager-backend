package com.hackathon.event.repository;

import com.hackathon.event.model.Education;
import com.hackathon.event.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
