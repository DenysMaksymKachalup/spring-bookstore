package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.user.UserDto;
import com.example.springonlinebookstore.dto.user.UserRegistrationRequestDto;
import com.example.springonlinebookstore.model.User;
import org.mapstruct.Mapper;
import java.util.Optional;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto createUserRequestDto);

    UserDto toDto(User user);

}
