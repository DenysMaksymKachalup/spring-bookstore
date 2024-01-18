package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.springonlinebookstore.model.CartItem;
import com.example.springonlinebookstore.model.ShoppingCart;
import com.example.springonlinebookstore.repository.books.BookRepository;
import com.example.springonlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "shoppingCart",
            source = "shoppingCartId",
            qualifiedByName = "shoppingCartById")
    @Mapping(target = "book",
            source = "cartItemRequestDto.bookId",
            qualifiedByName = "bookById")
    CartItem toModel(
            Long shoppingCartId,
            CartItemRequestDto cartItemRequestDto,
            @Context ShoppingCartRepository shoppingCartRepository,
            @Context BookRepository bookRepository);

    @Named("shoppingCartById")
    default ShoppingCart shoppingCartById(
            Long shoppingCartId,
            @Context ShoppingCartRepository shoppingCartRepository) {
        return shoppingCartRepository.findById(shoppingCartId)
                .orElseGet(ShoppingCart::new);
    }
}
