package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.cartitem.CartItemQuantityRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.springonlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.springonlinebookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "shopping carts", description = "Endpoints for managing shopping carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping()
    @Operation(summary = "Find shopping cart by user id",
            description = "Find shopping cart by user id")
    public ShoppingCartDto findByUserId() {
        return shoppingCartService.findByUserId();
    }

    @PostMapping
    @Operation(summary = "Add book to shopping cart",
            description = "Add book to shopping cart")
    public CartItemResponseDto addBookToShoppingCart(
            @RequestBody @Valid CartItemRequestDto cartItemRequestDto) {
        return shoppingCartService.addBookToShoppingCart(cartItemRequestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Change quantity",
            description = "Change quantity by cart item id")
    public CartItemResponseDto changeQuantity(
            @PathVariable Long cartItemId,
            @RequestBody @Valid CartItemQuantityRequestDto cartItemQuantityRequestDto) {
        return shoppingCartService.changeQuantity(cartItemId, cartItemQuantityRequestDto);
    }

    @DeleteMapping("/cart-items/{id}")
    @Operation(summary = "Delete cart item",
            description = "Delete cart item by id")
    public ShoppingCartDto deleteCartItem(@PathVariable Long id) {
        return shoppingCartService.deleteCartItem(id);
    }
}
