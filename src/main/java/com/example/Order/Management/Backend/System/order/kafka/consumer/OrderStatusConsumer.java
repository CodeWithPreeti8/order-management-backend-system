package com.example.Order.Management.Backend.System.order.kafka.consumer;

import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.Order.Management.Backend.System.order.kafka.event.OrderStatusEvent;

@Service
public class OrderStatusConsumer {
	
	@KafkaListener(topics = "order-status-updated",groupId = "order-group")
    public void consume(OrderStatusEvent event) {

        System.out.println(
                "Received Event -> Order Id : "
                        + event.getOrderId()
                        + " Status : "
                        + event.getStatus()
        );
    }

}
