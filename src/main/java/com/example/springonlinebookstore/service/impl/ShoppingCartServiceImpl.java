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
import com.example.springonlinebookstore.repository.books.BookRepository;
import com.example.springonlinebookstore.repository.cartitems.CartItemRepository;
import com.example.springonlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import com.example.springonlinebookstore.repository.users.UserRepository;
import com.example.springonlinebookstore.service.BookService;
import com.example.springonlinebookstore.service.ShoppingCartService;
import java.util.Set;
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
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto findByUserId() {
        return shoppingCartMapper.toDto(findShoppingCart());
    }

    @Override
    public CartItemResponseDto addBookToShoppingCart(CartItemRequestDto cartItemRequestDto) {
        BookDto bookDto = bookService.findById(cartItemRequestDto.bookId());
        ShoppingCart shoppingCart = findShoppingCart();
        checkBookInCart(shoppingCart.getCartItems(), cartItemRequestDto.bookId());
        CartItem cartItem = cartItemMapper.toModel(
                shoppingCart.getId(),
                cartItemRequestDto,
                shoppingCartRepository,
                bookRepository);
        cartItemRepository.save(cartItem);
        cartItem.getBook().setTitle(bookDto.getTitle());
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public CartItemResponseDto changeQuantity(
            Long cartItemId, CartItemQuantityRequestDto cartItemQuantityRequestDto) {
        CartItem cartItem = findCartItemById(cartItemId);
        cartItem.setQuantity(cartItemQuantityRequestDto.quantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public ShoppingCartDto deleteCartItem(Long id) {
        findCartItemById(id);
        cartItemRepository.deleteById(id);
        return shoppingCartMapper.toDto(findShoppingCart());
    }

    private User getUserFromSecurityContextHolder() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserByEmail(user.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("User with email" + user.getEmail() + "not found!"));
    }

    private ShoppingCart findShoppingCart() {
        User user = getUserFromSecurityContextHolder();
        return shoppingCartRepository.findShoppingCartByUserId(user.getId()).orElseGet(() -> {
            ShoppingCart shoppingCartSave = new ShoppingCart();
            shoppingCartSave.setUser(user);
            return shoppingCartRepository.save(shoppingCartSave);
        });
    }

    private void checkBookInCart(Set<CartItem> cartItems, Long bookId) {
        cartItems.stream()
                .map(cartItem -> cartItem.getBook().getId())
                .filter(bookId::equals)
                .findFirst()
                .ifPresent(s -> {
                    throw new RuntimeException(
                            "Book with id: " + bookId + " is already in the cart!");
                });
    }

    private CartItem findCartItemById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("CartItem with id: " + id + " not found!"));
    }
}
