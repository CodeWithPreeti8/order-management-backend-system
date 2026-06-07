package com.example.Order.Management.Backend.System.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Order.Management.Backend.System.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
