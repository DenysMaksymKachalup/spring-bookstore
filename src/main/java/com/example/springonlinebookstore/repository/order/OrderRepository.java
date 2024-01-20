package com.example.springonlinebookstore.repository.order;

import com.example.springonlinebookstore.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserId(Long userId);
}
