package com.hackathon.event.repository;

import com.hackathon.event.model.Participant;
import com.hackathon.event.model.Progress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.mail.Part;
import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("SELECT p from Participant p WHERE p.registration.event.eventId = ?1")
    Page<Participant> getAllParticipantsByEventId(Long eventId, Pageable pageable);
    @Query("SELECT p from Participant p WHERE p.registration.event.eventId = :eventId")
    List<Participant> findAllByEventId(@Param("eventId") Long eventId);
    @Query("SELECT p.progress FROM Participant p WHERE p.participantId = :participantId")
    List<Progress> findAllProgressByParticipantId(@Param("participantId") Long participantId);
}
