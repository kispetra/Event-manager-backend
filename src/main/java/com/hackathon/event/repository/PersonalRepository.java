package com.hackathon.event.repository;

import com.hackathon.event.model.Personal;
import com.hackathon.event.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
}
