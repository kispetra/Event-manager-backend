package com.hackathon.event.repository;

import com.hackathon.event.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    @Query("SELECT r from Registration r WHERE r.event.eventId = ?1")
    Page<Registration> getAllRegistrationsByEventId(Long eventId, Pageable pageable);

    @Query("SELECT r from Registration r WHERE r.event.eventId = ?1")
    List<Registration> getAllUserRegistrationsByEventId(Long eventId);


}
