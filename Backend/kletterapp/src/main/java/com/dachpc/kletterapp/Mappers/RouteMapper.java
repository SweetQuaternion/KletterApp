package com.dachpc.kletterapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.dachpc.kletterapp.Dtos.RouteCreateDTO;
import com.dachpc.kletterapp.Dtos.RouteResponseDTO;
import com.dachpc.kletterapp.Entities.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "wand")
    @Mapping(ignore = true, target = "isActive")
    Route toEntity(RouteCreateDTO dto);

    @Mapping(target = "hallenId", source = "wand.hallenId")
    @Mapping(target = "wandNr", source = "wand.wandNr")
    RouteResponseDTO toResponseDTO(Route entity);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "wand")
    @Mapping(ignore = true, target = "isActive")
    void updateEntity(RouteCreateDTO dto, @MappingTarget Route entity);

}