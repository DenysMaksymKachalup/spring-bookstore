package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.cartitem.CartItemQuantityRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.springonlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import com.example.springonlinebookstore.mapper.CartItemMapper;
import com.example.springonlinebookstore.mapper.ShoppingCartMapper;
import com.example.springonlinebookstore.model.CartItem;
import com.example.springonlinebookstore.model.ShoppingCart;
import com.example.springonlinebookstore.model.User;
import com.example.springonlinebookstore.repository.cartitems.CartItemRepository;
import com.example.springonlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import com.example.springonlinebookstore.repository.users.UserRepository;
import com.example.springonlinebookstore.service.BookService;
import com.example.springonlinebookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final BookService bookService;
    @Transactional(readOnly = true)
    @Override
    public ShoppingCartDto findByUser() {
        ShoppingCart shoppingCart = getShoppingCart();
        Set<CartItem> cartItemsByShoppingCartId = cartItemRepository
                .findCartItemsByShoppingCartId(shoppingCart.getId());
        shoppingCart.setCartItems(cartItemsByShoppingCartId);
        ShoppingCartDto dto = shoppingCartMapper.toDto(shoppingCart);
        return dto;
    }

    @Override
    public CartItemResponseDto addBookToShoppingCart(CartItemRequestDto cartItemRequestDto) {
        bookService.findById(cartItemRequestDto.bookId());
        ShoppingCart shoppingCart = getShoppingCart();
        CartItem cartItem = cartItemMapper.toModel(shoppingCart.getId(), cartItemRequestDto);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemResponseDto changeQuantity(
            Long cartItemId, CartItemQuantityRequestDto cartItemQuantityRequestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("CartItem with id: " + cartItemId + " not found!"));
        cartItem.setQuantity(cartItemQuantityRequestDto.quantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public ShoppingCartDto deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("CartItem with id: " + cartItemId + " not found!"));
        cartItemRepository.delete(cartItem);

        ShoppingCart shoppingCart = getShoppingCart();
        shoppingCart.setCartItems(cartItemRepository
                .findCartItemsByShoppingCartId(shoppingCart.getId()));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    private User getUserFromSecurityContext() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("User with email" + email + "not found!"));
    }

    private ShoppingCart getShoppingCart() {
        User user = getUserFromSecurityContext();
        return shoppingCartRepository.findShoppingCartByUserId(user.getId()).orElseGet(() -> {
            ShoppingCart shoppingCartSave = new ShoppingCart();
            shoppingCartSave.setUser(user);
            return shoppingCartRepository.save(shoppingCartSave);
        });
    }
}
