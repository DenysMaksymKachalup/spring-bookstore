package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.springonlinebookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {ShoppingCartMapper.class, BookMapper.class})
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
    CartItem toModel(Long shoppingCartId, CartItemRequestDto cartItemRequestDto);

}
