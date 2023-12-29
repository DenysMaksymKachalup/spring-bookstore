package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.user.CreateUserRequestDto;
import com.example.springonlinebookstore.dto.user.UserDto;
import com.example.springonlinebookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(CreateUserRequestDto createUserRequestDto);

    UserDto toDto(User user);
}
