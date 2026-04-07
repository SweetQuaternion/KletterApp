package com.dachpc.kletterapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.dachpc.kletterapp.Dtos.AscentCreateDTO;
import com.dachpc.kletterapp.Dtos.AscentResponseDTO;
import com.dachpc.kletterapp.Entities.Ascent;

@Mapper(componentModel = "spring")
public interface AscentMapper {
    
    @Mapping(ignore = true, target = "id")
    Ascent toEntity(AscentCreateDTO dto);
    
    AscentResponseDTO toResponseDTO(Ascent ascent);

    @Mapping(ignore = true, target = "id")
    Ascent updateEntity(AscentCreateDTO dto, @MappingTarget Ascent ascent);

}
