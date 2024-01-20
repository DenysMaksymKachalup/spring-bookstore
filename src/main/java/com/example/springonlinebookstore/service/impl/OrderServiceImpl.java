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
import com.example.springonlinebookstore.model.ShoppingCart;
import com.example.springonlinebookstore.model.User;
import com.example.springonlinebookstore.repository.cartitems.CartItemRepository;
import com.example.springonlinebookstore.repository.order.OrderRepository;
import com.example.springonlinebookstore.repository.orderitem.OrderItemRepository;
import com.example.springonlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import com.example.springonlinebookstore.repository.users.UserRepository;
import com.example.springonlinebookstore.service.BookService;
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
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;
    private final BookService bookService;

    // TODO: 19.01.2024  method which delete all cartItem in shoppingCart
    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Set<CartItem> сartItems = getСartItems();

        Order order = new Order();
        order.setShippingAddress(orderRequestDto.shippingAddress());
        order.setTotal(countTotal(сartItems));
        order.setUser(getUserFromSecurityHolder());
        order.setOrderItems(createOrderItems(order, сartItems));

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

    @Override
    public OrderRequestDto updateStatusOrder(OrderUpdateRequestDto orderUpdateRequestDto) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderItemResponseDto> findAllOrderItemsInOrderById(Long orderId) {
        return orderItemRepository.findOrderItemByOrderId(orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderItemResponseDto findOrderItemInOrderById(Long orderItemId, Long orderId) {
        return null;
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

    private Set<CartItem> getСartItems() {
        Set<CartItem> cartItems = cartItemRepository
                .findCartItemsByShoppingCartId(getUserFromSecurityHolder().getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Shopping cart is empty!");
        }
        return  cartItems;
    }
}
