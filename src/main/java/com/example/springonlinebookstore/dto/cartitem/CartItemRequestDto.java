package com.example.springonlinebookstore.dto.cartitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CartItemRequestDto(
        @NotBlank Long bookId,
        @Size(min = 1) int quantity
) {
}
