package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.user.UserDto;
import com.example.springonlinebookstore.dto.user.UserLoginRequestDto;
import com.example.springonlinebookstore.dto.user.UserLoginResponseDto;
import com.example.springonlinebookstore.dto.user.UserRegistrationRequestDto;
import com.example.springonlinebookstore.security.AuthenticationService;
import com.example.springonlinebookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/registration")
    public UserDto register(@RequestBody
                            @Valid UserRegistrationRequestDto userRegistrationRequestDto) {
        return userService.register(userRegistrationRequestDto);
    }

}
