package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.order.OrderRequestDto;
import com.example.springonlinebookstore.dto.order.OrderResponseDto;
import com.example.springonlinebookstore.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }
}
