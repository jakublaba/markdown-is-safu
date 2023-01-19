package com.krabelard.safeapp.mapper;


import com.krabelard.safeapp.dto.UserDTO;
import com.krabelard.safeapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User dtoToEntity(UserDTO dto);

    UserDTO entityToDto(User entity);

}
