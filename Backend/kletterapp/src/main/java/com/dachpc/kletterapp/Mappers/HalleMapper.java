package com.dachpc.kletterapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dachpc.kletterapp.Dtos.HalleCreateDTO;
import com.dachpc.kletterapp.Dtos.HalleResponseDTO;
import com.dachpc.kletterapp.Entities.Halle;

@Mapper(componentModel = "spring")
public interface HalleMapper {
    
    @Mapping(ignore = true, target = "id")
    Halle toEntity(HalleCreateDTO dto);
    
    HalleResponseDTO toResponseDTO(Halle entity);

}
