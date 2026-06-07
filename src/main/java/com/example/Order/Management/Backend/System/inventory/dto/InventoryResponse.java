package com.example.Order.Management.Backend.System.inventory.dto;

import java.time.LocalDateTime;

public class InventoryResponse {
	
	private Long id;
    private Long productId;
    private Integer availableQuantity;
    private LocalDateTime lastUpdatedAt;

    public InventoryResponse() {
    }

    public InventoryResponse(Long id,
                             Long productId,
                             Integer availableQuantity,
                             LocalDateTime lastUpdatedAt) {

        this.id = id;
        this.productId = productId;
        this.availableQuantity = availableQuantity;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

}
