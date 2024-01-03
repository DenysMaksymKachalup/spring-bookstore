package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.user.UserDto;
import com.example.springonlinebookstore.dto.user.UserRegistrationRequestDto;
import com.example.springonlinebookstore.exception.RegistrationException;
import com.example.springonlinebookstore.mapper.UserMapper;
import com.example.springonlinebookstore.model.User;
import com.example.springonlinebookstore.repository.users.UserRepository;
import com.example.springonlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        String email = userRegistrationRequestDto.getEmail();
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new RegistrationException("User with email "
                    + email + " already exist!");
        }
        User user = userRepository.save(userMapper.toModel(userRegistrationRequestDto));
        return userMapper.toDto(user);
    }
}
