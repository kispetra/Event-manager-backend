package com.hackathon.event.repository;

import com.hackathon.event.dto.EventResponseDto;
import com.hackathon.event.model.Event;
import com.hackathon.event.model.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long>, PagingAndSortingRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.confirmationNotAfter < CURRENT_DATE AND e.invitesSent = false")
    List<Event> findAllWhereConfirmationDateExpiredAndInvitesSentFalse();
    @Query("SELECT e FROM Event e WHERE e.appUser.userid = :userId")
    List<Event> findByUserId(@Param("userId") Long userId);

    Page<Event> findByNameContaining(String name, Pageable pageable);

}
