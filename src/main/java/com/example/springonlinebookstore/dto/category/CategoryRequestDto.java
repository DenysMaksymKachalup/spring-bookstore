package com.example.springonlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank @Size(max = 50) String name,
        @Size(max = 250) String description
) {
}
