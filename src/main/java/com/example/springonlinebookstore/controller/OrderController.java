package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.order.OrderRequestDto;
import com.example.springonlinebookstore.dto.order.OrderResponseDto;
import com.example.springonlinebookstore.dto.order.OrderUpdateRequestDto;
import com.example.springonlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.springonlinebookstore.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponseDto> findAllOrders() {
        return orderService.findAllOrders();
    }

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public OrderResponseDto updateStatusOrder(@PathVariable Long id,
                                               @RequestBody @Valid OrderUpdateRequestDto orderUpdateDto){
        return orderService.updateStatusOrder(id,orderUpdateDto);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> findAllOrderItemsInOrderById(@PathVariable Long orderId) {
        return orderService.findAllOrderItemsInOrderById(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto findOrderItemInOrderById(@PathVariable Long itemId,
                                                         @PathVariable Long orderId) {
        return orderService.findOrderItemInOrderById(itemId,orderId);
    }
}
