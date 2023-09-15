package com.hackathon.event.repository;

import com.hackathon.event.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findByName(String name);
    Skill findFirstByName(String name);
    List<Skill> findByEventEventId(Long eventId);
}
