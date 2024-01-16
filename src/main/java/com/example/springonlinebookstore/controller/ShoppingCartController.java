package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.cartitem.CartItemQuantityRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.springonlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.springonlinebookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping()
    public ShoppingCartDto findByUser() {
        return shoppingCartService.findByUser();
    }

    @PostMapping
    public CartItemResponseDto addBookToShoppingCart(
            @RequestBody CartItemRequestDto cartItemRequestDto) {
        return shoppingCartService.addBookToShoppingCart(cartItemRequestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    public CartItemResponseDto changeQuantity(
            @PathVariable Long cartItemId,
            @RequestBody CartItemQuantityRequestDto cartItemQuantityRequestDto) {
        return shoppingCartService.changeQuantity(cartItemId, cartItemQuantityRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{id}")
    public ShoppingCartDto deleteCartItem(@PathVariable Long id) {
        return shoppingCartService.deleteCartItem(id);
    }
}
