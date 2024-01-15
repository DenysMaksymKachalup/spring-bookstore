package com.example.springonlinebookstore.dto.shoppingCart;

import com.example.springonlinebookstore.dto.cartItem.CartItemDto;
import java.util.Set;

public record ShoppingCartDto(
        Long id,
        Long userId,
        Set<CartItemDto> cartItemsDto
) {
}
