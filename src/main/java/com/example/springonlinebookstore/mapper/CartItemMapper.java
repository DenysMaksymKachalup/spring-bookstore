package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.springonlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.springonlinebookstore.model.CartItem;
import com.example.springonlinebookstore.model.ShoppingCart;
import java.util.Optional;
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
    CartItem toModel(Long shoppingCartId, CartItemRequestDto cartItemRequestDto);

    @Named("shoppingCartById")
    default ShoppingCart shoppingCartById(Long shoppingCartId) {
        return Optional.ofNullable(shoppingCartId)
                .map(ShoppingCart::new)
                .orElse(null);
    }
}
