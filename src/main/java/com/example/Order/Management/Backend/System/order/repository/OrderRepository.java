package com.example.Order.Management.Backend.System.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Order.Management.Backend.System.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
