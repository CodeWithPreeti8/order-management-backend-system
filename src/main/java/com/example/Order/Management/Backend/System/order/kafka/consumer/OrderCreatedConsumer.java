package com.example.Order.Management.Backend.System.order.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.Order.Management.Backend.System.inventory.service.InventoryService;
import com.example.Order.Management.Backend.System.order.kafka.event.OrderCreatedEvent;

@Service
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;

    public OrderCreatedConsumer(
            InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-group"
    )
    public void consume(
            OrderCreatedEvent event) {

        inventoryService.reduceStock(
                event.getProductId(),
                event.getQuantity());

        System.out.println(
                "Inventory Updated Through Kafka");
    }
}
