package com.example.Order.Management.Backend.System.order.kafka.event;

public class OrderStatusEvent {
	
	private Long orderId;

    private String status;

    public OrderStatusEvent() {
    }

    public OrderStatusEvent(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
