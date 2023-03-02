package com.hackathon.event.mapper;

import com.hackathon.event.dto.NameRequestDto;
import com.hackathon.event.model.Name;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface NameMapper {
    NameRequestDto toDto(Name name);
    Name toEntity(NameRequestDto nameRequestDto); 
}
