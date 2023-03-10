package com.hackathon.event.repository;

import com.hackathon.event.model.Flow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowRepository extends JpaRepository<Flow, Long> {
}
