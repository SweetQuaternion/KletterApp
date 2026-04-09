package com.dachpc.kletterapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.dachpc.kletterapp.Dtos.UserDTO;
import com.dachpc.kletterapp.Entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(ignore = true, target = "bildUrl")
    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);

    @Mapping(ignore = true, target = "bildUrl")
    User updateEntity(UserDTO dto, @MappingTarget User user);

}
