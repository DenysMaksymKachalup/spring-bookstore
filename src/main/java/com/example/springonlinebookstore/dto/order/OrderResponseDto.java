package com.example.springonlinebookstore.dto.order;

import com.example.springonlinebookstore.dto.orderitem.OrderItemResponseDto;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(
        Long id,
        Long user_id,
        Set<OrderItemResponseDto> orderItems,
        LocalDateTime orderDate,
        int total,
        String status
) {
}
