package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.springonlinebookstore.model.ShoppingCart;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Named("shoppingCartById")
    default ShoppingCart shoppingCartById(Long shoppingCartId) {
        return Optional.ofNullable(shoppingCartId)
                .map(ShoppingCart::new)
                .orElse(null);
    }
}
