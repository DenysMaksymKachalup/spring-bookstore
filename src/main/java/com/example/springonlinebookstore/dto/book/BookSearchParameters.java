package com.example.springonlinebookstore.dto.book;

import jakarta.validation.constraints.NotBlank;

public record BookSearchParameters(
        @NotBlank String[] titles,
        @NotBlank String[] authors) {
}
