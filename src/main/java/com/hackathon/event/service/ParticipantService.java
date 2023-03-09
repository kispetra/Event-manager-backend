package com.hackathon.event.service;

import com.hackathon.event.dto.ProgressFlowRequestDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ParticipantService {
    void saveProgress(Long eventId, Long participantId, Integer week_no, ProgressFlowRequestDto progressFlowRequestDto);
}

