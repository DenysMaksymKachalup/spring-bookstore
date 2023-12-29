package com.example.springonlinebookstore.dto.book;

import jakarta.validation.constraints.NotEmpty;

public record BookSearchParameters(
        @NotEmpty String[] titles,
        @NotEmpty String[] authors) {
}
