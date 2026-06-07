package com.example.Order.Management.Backend.System.product.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.Order.Management.Backend.System.product.entity.Product;
import com.example.Order.Management.Backend.System.product.repository.ProductRepository;
import org.springframework.cache.annotation.Caching;

import java.util.List;

@Service
public class ProductService {
	
	private final ProductRepository repository;
	public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // CREATE
	@CacheEvict(value = "allProducts", allEntries = true)
    public Product create(Product product) {
        return repository.save(product);
    }

    // GET BY ID
    @Cacheable(value = "products", key = "#id")
    public Product getById(Long id) {
    	
    	System.out.println("Fetching Product From Database...");
    	return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // GET ALL
    @Cacheable("allProducts")
    public List<Product> getAll() {
    	
    	System.out.println("Fetching Product List From Database...");
        return repository.findAll();
    }

    // UPDATE
    @CacheEvict(
    	    value = {"products", "allProducts"},
    	    key = "#id",
    	    allEntries = true
    	)
    public Product update(Long id, Product updatedProduct) {
        Product existing = getById(id);

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setCategory(updatedProduct.getCategory());
        existing.setPrice(updatedProduct.getPrice());
        existing.setQuantity(updatedProduct.getQuantity());

        return repository.save(existing);
    }

    @Caching(evict = {
    	    @CacheEvict(value = "products", key = "#id"),
    	    @CacheEvict(value = "allProducts", allEntries = true)
    	})
    public void delete(Long id) {
    	if (!repository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        repository.deleteById(id);
    }
    

}
