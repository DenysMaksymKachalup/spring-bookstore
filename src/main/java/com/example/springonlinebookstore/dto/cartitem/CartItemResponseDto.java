package com.example.springonlinebookstore.dto.cartitem;

public record CartItemResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity
) {
}
