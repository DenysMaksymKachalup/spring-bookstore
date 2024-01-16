package com.example.springonlinebookstore.dto.cartitem;

import jakarta.validation.constraints.Size;

public record CartItemQuantityRequestDto(
        @Size(min = 1) int quantity
) {
}
