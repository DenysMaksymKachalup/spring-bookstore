package com.example.springonlinebookstore.repository.cartitems;

import com.example.springonlinebookstore.model.CartItem;
import com.example.springonlinebookstore.model.ShoppingCart;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Set<CartItem> findCartItemsByShoppingCartId(Long shoppingCartId);
}
