package com.example.springonlinebookstore.repository.orderitem;

import com.example.springonlinebookstore.model.OrderItem;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Set<OrderItem> findOrderItemsByOrderId(Long orderId);

    Optional<OrderItem> findOrderItemByOrderIdAndId(Long orderItemId, Long orderId);
}
