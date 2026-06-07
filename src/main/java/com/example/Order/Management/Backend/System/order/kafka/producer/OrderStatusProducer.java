package com.example.Order.Management.Backend.System.order.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.Order.Management.Backend.System.order.kafka.event.OrderStatusEvent;

@Service
public class OrderStatusProducer {
	private final KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;

    public OrderStatusProducer(
            KafkaTemplate<String, OrderStatusEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderStatusEvent(
            OrderStatusEvent event) {

        kafkaTemplate.send(
                "order-status-updated",
                event
        );

        System.out.println(
                "Event Published : "
                        + event.getOrderId()
                        + " "
                        + event.getStatus()
        );
    }

}
