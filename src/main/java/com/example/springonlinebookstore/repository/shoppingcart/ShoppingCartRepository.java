package com.example.springonlinebookstore.repository.shoppingcart;

import com.example.springonlinebookstore.model.ShoppingCart;
import com.example.springonlinebookstore.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    Optional<ShoppingCart> findShoppingCartByUser(User user);
}
