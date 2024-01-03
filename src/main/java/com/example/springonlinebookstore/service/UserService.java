package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.user.UserDto;
import com.example.springonlinebookstore.dto.user.UserRegistrationRequestDto;
import com.example.springonlinebookstore.exception.RegistrationException;

public interface UserService {
    UserDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;

}
