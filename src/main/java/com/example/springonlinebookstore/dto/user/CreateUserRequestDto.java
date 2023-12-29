package com.example.springonlinebookstore.dto.user;


import com.example.springonlinebookstore.annotation.FieldsValueMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@FieldsValueMatch(field = "password", fieldMatch = "repeatPassword")
public class CreateUserRequestDto {

    @Email
    private String email;

    @Length(min = 6, max = 20)
    private String password;

    @Length(min = 6, max = 20)
    private String repeatPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String shippingAddress;
}

