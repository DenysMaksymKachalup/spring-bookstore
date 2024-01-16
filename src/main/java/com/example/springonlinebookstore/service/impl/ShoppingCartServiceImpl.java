package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.book.BookDto;
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

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final BookService bookService;

    @Override
    public ShoppingCartDto findByUser() {
        ShoppingCart shoppingCart = getShoppingCart();

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public CartItemResponseDto addBookToShoppingCart(CartItemRequestDto cartItemRequestDto) {
        BookDto bookDto = bookService.findById(cartItemRequestDto.bookId());
        ShoppingCart shoppingCart = getShoppingCart();
        CartItem cartItem = cartItemMapper.toModel(shoppingCart.getId(), cartItemRequestDto);
        cartItemRepository.save(cartItem);
        cartItem.getBook().setTitle(bookDto.getTitle());
        return cartItemMapper.toDto(cartItem);
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
    public ShoppingCartDto deleteCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("CartItem with id: " + id + " not found!"));
        cartItemRepository.deleteById(id);

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
