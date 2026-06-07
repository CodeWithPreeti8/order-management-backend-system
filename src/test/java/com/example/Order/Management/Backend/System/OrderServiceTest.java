package com.example.Order.Management.Backend.System;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Order.Management.Backend.System.exception.InvalidQuantityException;
import com.example.Order.Management.Backend.System.exception.ProductNotFoundException;
import com.example.Order.Management.Backend.System.order.dto.CreateOrderRequest;
import com.example.Order.Management.Backend.System.order.dto.OrderItemRequest;
import com.example.Order.Management.Backend.System.order.dto.response.OrderResponse;
import com.example.Order.Management.Backend.System.order.entity.Order;
import com.example.Order.Management.Backend.System.order.entity.OrderStatus;
import com.example.Order.Management.Backend.System.order.repository.OrderRepository;
import com.example.Order.Management.Backend.System.order.service.OrderService;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private OrderService orderService;
	
	@Test
	public void testCreateOrder() {
		CreateOrderRequest request = new CreateOrderRequest();
		OrderItemRequest item1 = new OrderItemRequest();
		item1.setProductId(1L);
		item1.setQuantity(2);
		
		OrderItemRequest item2 = new OrderItemRequest();

		item2.setProductId(2L);
		item2.setQuantity(1);
		
		request.setItems(List.of(item1, item2));
		when(orderRepository.save(any(Order.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
		
		OrderResponse orderResponse = orderService.createOrder(request);
		assertNotNull(orderResponse);

		assertEquals(new BigDecimal("175000"),orderResponse.getTotalAmount());

		assertEquals(OrderStatus.PENDING,orderResponse.getStatus());

		assertEquals(2,orderResponse.getItems().size());
		
		verify(orderRepository).save(any(Order.class));
		
	}
	
	@Test
	void testCreateOrder_ProductNotFound() {

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
	            exception.getMessage()
	    );
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
	            exception.getMessage()
	    );
	}

}
