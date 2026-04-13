package com.dachpc.kletterapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.dachpc.kletterapp.Dtos.UserCreateDTO;
import com.dachpc.kletterapp.Dtos.UserResponseDTO;
import com.dachpc.kletterapp.Entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(ignore = true, target = "bildUrl")
    User toEntity(UserResponseDTO dto);

    UserResponseDTO toDTO(User user);

    @Mapping(ignore = true, target = "bildUrl")
    @Mapping(ignore = true, target = "punkte")
    @Mapping(ignore = true, target = "ascentCount")
    User updateEntity(UserCreateDTO dto, @MappingTarget User user);

}
