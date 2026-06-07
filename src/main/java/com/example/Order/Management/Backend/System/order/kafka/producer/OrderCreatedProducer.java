package com.example.Order.Management.Backend.System.order.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.Order.Management.Backend.System.order.kafka.event.OrderCreatedEvent;

@Service
public class OrderCreatedProducer {
	
	 private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

	    public OrderCreatedProducer(
	            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
	        this.kafkaTemplate = kafkaTemplate;
	    }

	    public void publishOrderCreatedEvent(
	            OrderCreatedEvent event) {

	        kafkaTemplate.send(
	                "order-created",
	                event
	        );

	        System.out.println(
	                "Order Created Event Published -> ProductId: "
	                        + event.getProductId()
	                        + " Quantity: "
	                        + event.getQuantity()
	        );
	    }

}
