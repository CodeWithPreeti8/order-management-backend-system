package com.example.Order.Management.Backend.System.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemRequest {
	@NotNull(message = "ProductId cannot be null")
	private Long productId;
	
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;
	
	public OrderItemRequest() {
		
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	

}
