package com.krabelard.safeapp.mapper;


import com.krabelard.safeapp.dto.UserDTO;
import com.krabelard.safeapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO entityToDto(User entity);

}
