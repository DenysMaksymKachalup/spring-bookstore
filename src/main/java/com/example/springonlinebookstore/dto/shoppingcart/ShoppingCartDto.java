package com.example.springonlinebookstore.dto.shoppingcart;

import com.example.springonlinebookstore.dto.cartitem.CartItemResponseDto;
import java.util.Set;

public record ShoppingCartDto(
        Long id,
        Long userId,
        Set<CartItemResponseDto> cartItems
) {
}
