package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.user.UserDto;
import com.example.springonlinebookstore.dto.user.UserRegistrationRequestDto;
import com.example.springonlinebookstore.exception.RegistrationException;
import com.example.springonlinebookstore.mapper.UserMapper;
import com.example.springonlinebookstore.model.User;
import com.example.springonlinebookstore.model.enumeration.RoleName;
import com.example.springonlinebookstore.repository.roles.RoleRepository;
import com.example.springonlinebookstore.repository.users.UserRepository;
import com.example.springonlinebookstore.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        String email = userRegistrationRequestDto.getEmail();
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new RegistrationException("User with email "
                    + email + " already exist!");
        }
        User modelForSaving = userMapper.toModel(userRegistrationRequestDto);

        modelForSaving.setRoles(Set.of(roleRepository
                .findRoleByName(RoleName.ROLE_USER)));
        modelForSaving.setPassword(passwordEncoder
                .encode(userRegistrationRequestDto.getPassword()));

        return userMapper.toDto(userRepository.save(modelForSaving));
    }
}
