package com.example.Order.Management.Backend.System.product.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Order.Management.Backend.System.product.entity.Product;
import com.example.Order.Management.Backend.System.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/products")
@Tag(
        name = "Product APIs",
        description = "Operations related to product management"
)
public class ProductController {
	
	private final ProductService service;
	public ProductController(ProductService service) {
        this.service = service;
    }

	@Operation(
	        summary = "Create Product",
	        description = "Creates a new product in the system"
	)
    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @Operation(
            summary = "Get Product By Id",
            description = "Fetch product details using product id"
    )
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.getById(id);
    }
    
    @Operation(
            summary = "Get All Products",
            description = "Retrieve all products from the system"
    )
    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    
    @Operation(
            summary = "Update Product",
            description = "Updates product details using product id"
    )
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,
                          @RequestBody Product product) {
        return service.update(id, product);
    }

    @Operation(
            summary = "Delete Product",
            description = "Deletes a product using product id"
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
