package com.hackathon.event.service;

import com.hackathon.event.dto.FlowRequestDto;

public interface ParticipantService {
    void saveProgress(Long eventId, Long participantId, Integer week_no, FlowRequestDto flowRequestDto);
}

