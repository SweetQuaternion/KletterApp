package com.dachpc.kletterapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.dachpc.kletterapp.Dtos.NewsCreateDTO;
import com.dachpc.kletterapp.Dtos.NewsResponseDTO;
import com.dachpc.kletterapp.Entities.News;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "datum")
    @Mapping(ignore = true, target = "user")
    @Mapping(ignore = true, target = "halle")
    News toEntity(NewsCreateDTO dto);
    
    @Mapping(target = "autor", source = "user.name")
    @Mapping(target = "hallenId", source = "halle.id")
    NewsResponseDTO toResponseDTO(News entity);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "datum")
    @Mapping(ignore = true, target = "user")
    @Mapping(ignore = true, target = "halle")
    void updateEntity(NewsCreateDTO dto, @MappingTarget News entity);
}
