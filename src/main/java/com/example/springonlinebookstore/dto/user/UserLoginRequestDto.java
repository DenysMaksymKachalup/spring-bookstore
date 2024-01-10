package com.example.springonlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginRequestDto {
    @Email
    private String email;

    @Length(min = 6, max = 20)
    private String password;
}
