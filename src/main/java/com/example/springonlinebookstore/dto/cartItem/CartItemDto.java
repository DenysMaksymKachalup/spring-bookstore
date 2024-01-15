package com.example.springonlinebookstore.dto.cartItem;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        Long quantity
) {
}
