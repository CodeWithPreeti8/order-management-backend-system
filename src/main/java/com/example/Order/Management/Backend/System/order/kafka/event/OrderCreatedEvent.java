package com.example.Order.Management.Backend.System.order.kafka.event;

public class OrderCreatedEvent {
	private Long productId;

    private Integer quantity;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(
            Long productId,
            Integer quantity) {

        this.productId = productId;
        this.quantity = quantity;
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
