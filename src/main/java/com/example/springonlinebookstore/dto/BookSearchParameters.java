package com.example.springonlinebookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record BookSearchParameters(
        @NotBlank String[] titles,
        @NotBlank String[] authors) {
}
