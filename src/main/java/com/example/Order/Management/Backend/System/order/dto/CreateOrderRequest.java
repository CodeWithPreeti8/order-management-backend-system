package com.example.Order.Management.Backend.System.order.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class CreateOrderRequest {
	
	private List<OrderItemRequest> items;
	
	public CreateOrderRequest() {
		
	}
	
	@NotEmpty(message = "Order must contain at least one item")
    @Valid
	public List<OrderItemRequest> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}
	
	
}
