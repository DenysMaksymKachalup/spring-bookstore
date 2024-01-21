package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.order.OrderRequestDto;
import com.example.springonlinebookstore.dto.order.OrderResponseDto;
import com.example.springonlinebookstore.dto.order.OrderUpdateRequestDto;
import com.example.springonlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.springonlinebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "orders", description = "Endpoints for managing order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Find all orders", description = "Find all orders")
    public List<OrderResponseDto> findAllOrders() {
        return orderService.findAllOrders();
    }

    @PostMapping
    @Operation(summary = "Create order", description = "Create order from shopping cart")
    public OrderResponseDto createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update order status",
            description = "Update order from shopping cart. Only admin has access")
    public OrderResponseDto updateStatusOrder(
            @PathVariable Long id,
            @RequestBody @Valid OrderUpdateRequestDto orderUpdateDto) {
        return orderService.updateStatusOrder(id, orderUpdateDto);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Find all orderItems order by id",
            description = "Find all orderItems in order by id")
    public List<OrderItemResponseDto> findAllOrderItemsInOrderById(@PathVariable Long orderId) {
        return orderService.findAllOrderItemsInOrderById(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Find all orderItems by id  order by id",
            description = "Find all orderItems by id  order by id")
    public OrderItemResponseDto findOrderItemInOrderById(@PathVariable Long itemId,
                                                         @PathVariable Long orderId) {
        return orderService.findOrderItemInOrderById(itemId, orderId);
    }
}
