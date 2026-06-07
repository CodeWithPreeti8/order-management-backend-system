package com.example.Order.Management.Backend.System.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Order.Management.Backend.System.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
