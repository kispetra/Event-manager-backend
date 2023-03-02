package com.hackathon.event.repository;

import com.hackathon.event.model.Registration;
import com.hackathon.event.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
