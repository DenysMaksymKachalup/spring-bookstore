package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.shoppingCart.ShoppingCartDto;
import com.example.springonlinebookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(config = MapperConfig.class,uses = UserMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id",target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

}
