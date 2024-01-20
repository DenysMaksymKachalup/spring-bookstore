package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.order.OrderRequestDto;
import com.example.springonlinebookstore.dto.order.OrderResponseDto;
import com.example.springonlinebookstore.dto.order.OrderUpdateRequestDto;
import com.example.springonlinebookstore.dto.orderitem.OrderItemResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> findAllOrders();

    OrderRequestDto updateStatusOrder(OrderUpdateRequestDto orderUpdateRequestDto);

    List<OrderItemResponseDto> findAllOrderItemsInOrderById(Long orderId);

    OrderItemResponseDto findOrderItemInOrderById(Long orderItemId, Long orderId);
}
