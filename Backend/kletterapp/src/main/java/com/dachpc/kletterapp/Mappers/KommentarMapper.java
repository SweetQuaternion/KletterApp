package com.dachpc.kletterapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dachpc.kletterapp.Dtos.KommentarCreateDTO;
import com.dachpc.kletterapp.Dtos.KommentarResponseDTO;
import com.dachpc.kletterapp.Entities.Kommentar;

@Mapper(componentModel = "spring")
public interface KommentarMapper {
    
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "route")
    @Mapping(ignore = true, target = "user")
    @Mapping(ignore = true, target = "datum")
    Kommentar toEntity(KommentarCreateDTO dto);

    @Mapping(target = "username", source = "user.name")
    @Mapping(target = "routenId", source = "route.id")
    KommentarResponseDTO toResponseDTO(Kommentar entity);

}
