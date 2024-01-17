package com.example.springonlinebookstore.dto.cartitem;

import jakarta.validation.constraints.Min;

public record CartItemQuantityRequestDto(
        @Min(1) int quantity
) {
}
