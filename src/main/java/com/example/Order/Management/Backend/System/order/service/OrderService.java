package com.example.Order.Management.Backend.System.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Order.Management.Backend.System.order.exception.InvalidOrderStatusTransitionException;
import com.example.Order.Management.Backend.System.order.exception.InvalidQuantityException;
import com.example.Order.Management.Backend.System.order.exception.OrderNotFoundException;
import com.example.Order.Management.Backend.System.order.exception.ProductNotFoundException;
import com.example.Order.Management.Backend.System.order.kafka.event.OrderCreatedEvent;
import com.example.Order.Management.Backend.System.order.kafka.event.OrderStatusEvent;
import com.example.Order.Management.Backend.System.order.kafka.producer.OrderCreatedProducer;
import com.example.Order.Management.Backend.System.order.kafka.producer.OrderStatusProducer;
import com.example.Order.Management.Backend.System.inventory.service.InventoryService;
import com.example.Order.Management.Backend.System.order.dto.CreateOrderRequest;
import com.example.Order.Management.Backend.System.order.dto.OrderItemRequest;
import com.example.Order.Management.Backend.System.order.dto.response.OrderItemResponse;
import com.example.Order.Management.Backend.System.order.dto.response.OrderResponse;
import com.example.Order.Management.Backend.System.order.entity.Order;
import com.example.Order.Management.Backend.System.order.entity.OrderItem;
import com.example.Order.Management.Backend.System.order.entity.OrderStatus;
import com.example.Order.Management.Backend.System.order.repository.OrderRepository;
import com.example.Order.Management.Backend.System.product.entity.Product;
import com.example.Order.Management.Backend.System.product.repository.ProductRepository;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final InventoryService inventoryService;
	private final OrderStatusProducer orderStatusProducer;
	private final OrderCreatedProducer orderCreatedProducer;
	
	public OrderService(OrderRepository orderRepository, ProductRepository productRepository, InventoryService inventoryService, OrderStatusProducer orderStatusProducer, OrderCreatedProducer orderCreatedProducer) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.inventoryService = inventoryService;
		this.orderStatusProducer = orderStatusProducer;
		this.orderCreatedProducer = orderCreatedProducer;
	}
	
	@Transactional
	public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = new Order();

        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {

            if (itemRequest.getQuantity() <= 0) {
                throw new InvalidQuantityException(
                        "Quantity must be greater than zero");
            }

            inventoryService.validateStock(
                    itemRequest.getProductId(),
                    itemRequest.getQuantity());

            BigDecimal price =
                    getProductPrice(itemRequest.getProductId());
            
            BigDecimal lineTotal =
                    price.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalAmount = totalAmount.add(lineTotal);

            OrderItem orderItem = new OrderItem();

            orderItem.setProductId(itemRequest.getProductId());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(price);

            order.addItem(orderItem);
        }
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        System.out.println("Entity Total = " + savedOrder.getTotalAmount());
        
        for (OrderItem item : savedOrder.getItems()) {

            OrderCreatedEvent event =
                    new OrderCreatedEvent(
                            item.getProductId(),
                            item.getQuantity()
                    );

            orderCreatedProducer.publishOrderCreatedEvent(event);
        }
        
        OrderResponse response = mapToResponse(savedOrder);

     // DEBUG
        System.out.println("DTO Total = " + response.getTotalAmount());

        return response;

    }
	
	public OrderResponse getOrderById(Long id) {

		Order order = orderRepository.findById(id)
	            .orElseThrow(() -> new OrderNotFoundException(
	                    "Order not found with id: " + id
	            ));

	    return mapToResponse(order);

	}
	
	public List<OrderResponse> getAllOrders() {
		return orderRepository.findAll()
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
	}
	
	
	private BigDecimal getProductPrice(Long productId) {

	    Product product = productRepository
	            .findById(productId)
	            .orElseThrow(() ->
	                    new ProductNotFoundException(
	                            "Product not found with id: "
	                                    + productId));

	    return product.getPrice();
	}
	
	// Helper Method . Temporary
	private OrderResponse mapToResponse(Order order) {

	    List<OrderItemResponse> itemResponses =
	            order.getItems().stream()
	                    .map(item -> new OrderItemResponse(
	                            item.getProductId(),
	                            item.getQuantity(),
	                            item.getPrice()
	                    ))
	                    .toList();

	    return new OrderResponse(
	            order.getId(),
	            order.getTotalAmount(),
	            order.getStatus(),
	            order.getCreatedAt(),
	            itemResponses
	    );
	}
	
	@Transactional
	public OrderResponse updateOrderStatus(
	        Long orderId,
	        OrderStatus status) {

	    Order order = orderRepository.findById(orderId)
	            .orElseThrow(() ->
	                    new OrderNotFoundException(
	                            "Order not found with id: " + orderId));
	    
	    validateStatusTransition(
	            order.getStatus(),
	            status);
	    
	    order.setStatus(status);

	    Order updatedOrder =
	            orderRepository.save(order);
	    
	    OrderStatusEvent event =
	            new OrderStatusEvent(
	                    updatedOrder.getId(),
	                    updatedOrder.getStatus().name()
	            );

	    orderStatusProducer.publishOrderStatusEvent(event);
	    return mapToResponse(updatedOrder);
	}
	
	private void validateStatusTransition(OrderStatus currentStatus,OrderStatus newStatus) {

	    switch (currentStatus) {

	        case PENDING:

	            if (newStatus != OrderStatus.CONFIRMED
	                    && newStatus != OrderStatus.CANCELLED) {

	                throw new InvalidOrderStatusTransitionException(
	                        "Cannot change status from "
	                                + currentStatus
	                                + " to "
	                                + newStatus);
	            }

	            break;

	        case CONFIRMED:

	            if (newStatus != OrderStatus.SHIPPED) {

	                throw new InvalidOrderStatusTransitionException(
	                        "Cannot change status from "
	                                + currentStatus
	                                + " to "
	                                + newStatus);
	            }

	            break;

	        case SHIPPED:

	            if (newStatus != OrderStatus.DELIVERED) {

	                throw new InvalidOrderStatusTransitionException(
	                        "Cannot change status from "
	                                + currentStatus
	                                + " to "
	                                + newStatus);
	            }

	            break;

	        case DELIVERED:
	        case CANCELLED:

	            throw new InvalidOrderStatusTransitionException(
	                    "Order is already "
	                            + currentStatus
	                            + ". Status cannot be changed.");
	    }
	}

}
