package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.cartitem.CartItemQuantityRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.springonlinebookstore.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto findByUserId();

    CartItemResponseDto addBookToShoppingCart(CartItemRequestDto cartItemRequestDto);

    CartItemResponseDto changeQuantity(Long cartItemId,
                                       CartItemQuantityRequestDto cartItemQuantityRequestDto);

    ShoppingCartDto deleteCartItem(Long cartItemId);

}
