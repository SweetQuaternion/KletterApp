package com.dachpc.kletterapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.dachpc.kletterapp.Dtos.WandCreateDTO;
import com.dachpc.kletterapp.Dtos.WandResponseDTO;
import com.dachpc.kletterapp.Entities.Wand;

@Mapper(componentModel = "spring", uses = {RouteMapper.class})
public interface WandMapper {
    
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "routen")
    Wand toEntity(WandCreateDTO dto);

    @Mapping(target = "hallenId", source = "id.hallenId")
    @Mapping(target = "wandNr", source = "id.wandNr")
    WandResponseDTO toResponseDTO(Wand entity);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "routen")
    void updateEntity(WandCreateDTO dto, @MappingTarget Wand entity);

}
