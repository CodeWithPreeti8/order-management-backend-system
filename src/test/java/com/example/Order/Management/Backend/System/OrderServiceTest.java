package com.example.Order.Management.Backend.System;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Order.Management.Backend.System.order.exception.ProductNotFoundException;
import com.example.Order.Management.Backend.System.order.exception.InvalidQuantityException;
import com.example.Order.Management.Backend.System.inventory.service.InventoryService;
import com.example.Order.Management.Backend.System.order.dto.CreateOrderRequest;
import com.example.Order.Management.Backend.System.order.dto.OrderItemRequest;
import com.example.Order.Management.Backend.System.order.dto.response.OrderResponse;
import com.example.Order.Management.Backend.System.order.entity.Order;
import com.example.Order.Management.Backend.System.order.entity.OrderStatus;
import com.example.Order.Management.Backend.System.order.kafka.producer.OrderCreatedProducer;
import com.example.Order.Management.Backend.System.order.kafka.producer.OrderStatusProducer;
import com.example.Order.Management.Backend.System.order.repository.OrderRepository;
import com.example.Order.Management.Backend.System.order.service.OrderService;
import com.example.Order.Management.Backend.System.product.entity.Product;
import com.example.Order.Management.Backend.System.product.repository.ProductRepository;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
	private ProductRepository productRepository;

	@Mock
	private InventoryService inventoryService;

	@Mock
	private OrderStatusProducer orderStatusProducer;

	@Mock
	private OrderCreatedProducer orderCreatedProducer;
	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private OrderService orderService;
	
	@Test
    void testCreateOrder() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setPrice(new BigDecimal("50000"));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setPrice(new BigDecimal("75000"));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product1));

        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product2));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderRequest request = new CreateOrderRequest();

        OrderItemRequest item1 = new OrderItemRequest();
        item1.setProductId(1L);
        item1.setQuantity(2);

        OrderItemRequest item2 = new OrderItemRequest();
        item2.setProductId(2L);
        item2.setQuantity(1);

        request.setItems(List.of(item1, item2));

        OrderResponse response =
                orderService.createOrder(request);

        assertNotNull(response);

        assertEquals(
                new BigDecimal("175000"),
                response.getTotalAmount());

        assertEquals(
                OrderStatus.PENDING,
                response.getStatus());

        assertEquals(
                2,
                response.getItems().size());

        verify(orderRepository)
                .save(any(Order.class));

        verify(orderCreatedProducer, org.mockito.Mockito.times(2))
                .publishOrderCreatedEvent(any());
    }

    @Test
    void testCreateOrder_ProductNotFound() {

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(999L);
        item.setQuantity(2);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setItems(List.of(item));

        ProductNotFoundException exception =
                assertThrows(
                        ProductNotFoundException.class,
                        () -> orderService.createOrder(request)
                );

        assertEquals(
                "Product not found with id: 999",
                exception.getMessage());
    }

    @Test
    void testCreateOrder_InvalidQuantity() {

        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(1L);
        item.setQuantity(0);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setItems(List.of(item));

        InvalidQuantityException exception =
                assertThrows(
                        InvalidQuantityException.class,
                        () -> orderService.createOrder(request)
                );

        assertEquals(
                "Quantity must be greater than zero",
                exception.getMessage());
    }

}
