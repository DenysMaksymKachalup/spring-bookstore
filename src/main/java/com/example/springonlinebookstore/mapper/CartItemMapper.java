package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.cartItem.CartItemDto;
import com.example.springonlinebookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);
}
