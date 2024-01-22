package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.order.OrderRequestDto;
import com.example.springonlinebookstore.dto.order.OrderResponseDto;
import com.example.springonlinebookstore.dto.order.OrderUpdateRequestDto;
import com.example.springonlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import com.example.springonlinebookstore.mapper.OrderItemMapper;
import com.example.springonlinebookstore.mapper.OrderMapper;
import com.example.springonlinebookstore.model.CartItem;
import com.example.springonlinebookstore.model.Order;
import com.example.springonlinebookstore.model.OrderItem;
import com.example.springonlinebookstore.model.User;
import com.example.springonlinebookstore.model.enumeration.OrderStatus;
import com.example.springonlinebookstore.repository.cartitems.CartItemRepository;
import com.example.springonlinebookstore.repository.order.OrderRepository;
import com.example.springonlinebookstore.repository.orderitem.OrderItemRepository;
import com.example.springonlinebookstore.repository.users.UserRepository;
import com.example.springonlinebookstore.service.OrderService;
import com.example.springonlinebookstore.service.ShoppingCartService;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Set<CartItem> cartItems = getCartItems();

        Order order = new Order();
        order.setShippingAddress(orderRequestDto.shippingAddress());
        order.setTotal(countTotal(cartItems));
        order.setUser(getUserFromSecurityHolder());
        order.setOrderItems(createOrderItems(order, cartItems));

        shoppingCartService.cleanShoppingCart();
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAllByUserId(getUserFromSecurityHolder().getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public OrderResponseDto updateStatusOrder(Long id, OrderUpdateRequestDto orderUpdateDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Order by id: " + id + " not found"));
        order.setStatus(OrderStatus.valueOf(orderUpdateDto.status()));
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderItemResponseDto> findAllOrderItemsInOrderById(Long orderId) {
        return orderItemRepository.findOrderItemsByOrderId(orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderItemResponseDto findOrderItemInOrderById(Long orderItemId, Long orderId) {
        OrderItem orderItem = orderItemRepository.findOrderItemByOrderIdAndId(orderItemId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OrderItem with ID: " + orderItemId
                                + " in order with id: " + orderId + " not found!"));
        return orderItemMapper.toDto(orderItem);
    }

    private User getUserFromSecurityHolder() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserByEmail(user.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("User with email" + user.getEmail() + "not found!"));
    }

    private BigDecimal countTotal(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> createOrderItems(Order order, Set<CartItem> cartItems) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setPrice(cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private Set<CartItem> getCartItems() {
        Set<CartItem> cartItems = cartItemRepository
                .findCartItemsByShoppingCartId(getUserFromSecurityHolder().getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Shopping cart is empty!");
        }
        return cartItems;
    }
}
