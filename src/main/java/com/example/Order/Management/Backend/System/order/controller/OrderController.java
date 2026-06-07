package com.example.Order.Management.Backend.System.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Order.Management.Backend.System.order.dto.CreateOrderRequest;
import com.example.Order.Management.Backend.System.order.dto.UpdateOrderStatusRequest;
import com.example.Order.Management.Backend.System.order.dto.response.OrderResponse;
import com.example.Order.Management.Backend.System.order.entity.Order;
import com.example.Order.Management.Backend.System.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
@Tag(
        name = "Order APIs",
        description = "Operations related to order management and order processing"
)
public class OrderController {
	
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Operation(
	        summary = "Create Order",
	        description = "Creates a new order and publishes inventory update events through Kafka"
	)
	@PostMapping
	public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
		return orderService.createOrder(request);
	}
	
	@Operation(
	        summary = "Get Order By Id",
	        description = "Fetch order details using order id"
	)
	@GetMapping("/{id}")
	public OrderResponse getOrderById(@PathVariable Long id) {
		return orderService.getOrderById(id);
	}
	
	@Operation(
	        summary = "Get All Orders",
	        description = "Retrieve all orders from the system"
	)
	@GetMapping
	public List<OrderResponse> getAllOrders() {
		return orderService.getAllOrders();
		
	}
	
	@Operation(
	        summary = "Update Order Status",
	        description = "Updates the status of an existing order"
	)
	@PutMapping("/{id}/status")
	public OrderResponse updateOrderStatus(
	        @PathVariable Long id,
	        @Valid @RequestBody UpdateOrderStatusRequest request) {

	    return orderService.updateOrderStatus(
	            id,
	            request.getStatus());
	}

}
