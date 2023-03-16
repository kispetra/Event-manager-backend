package com.hackathon.event.repository;

import com.hackathon.event.model.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("SELECT p from Participant p WHERE p.registration.event.eventId = ?1")
    Page<Participant> getAllParticipantsByEventId(Long eventId, Pageable pageable);
}
