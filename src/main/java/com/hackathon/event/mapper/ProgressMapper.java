package com.hackathon.event.mapper;

import com.hackathon.event.dto.FlowResponseDto;
import com.hackathon.event.dto.ProgressResponseDto;
import com.hackathon.event.model.Flow;
import com.hackathon.event.model.Progress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressMapper {

    public ProgressResponseDto toDto(Progress progress){
        ProgressResponseDto progressResponseDto=new ProgressResponseDto();
        FlowResponseDto flowResponseDto=new FlowResponseDto();

        progressResponseDto.setWeek_no(progress.getWeek());
        List<FlowResponseDto> flowList= new ArrayList<>();
        for(Flow flow: progress.getFlow()){
            flowResponseDto.setStatus(flow.getStatus());
            flowResponseDto.setComment(flow.getComment());
            flowList.add(flowResponseDto);
        }
        progressResponseDto.setFlowList(flowList);

        return progressResponseDto;
    }

}
