package com.example.Order.Management.Backend.System.inventory.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Order.Management.Backend.System.inventory.dto.InventoryRequest;
import com.example.Order.Management.Backend.System.inventory.dto.InventoryResponse;
import com.example.Order.Management.Backend.System.inventory.entity.Inventory;
import com.example.Order.Management.Backend.System.inventory.exception.InsufficientStockException;
import com.example.Order.Management.Backend.System.inventory.exception.InventoryNotFoundException;
import com.example.Order.Management.Backend.System.inventory.repository.InventoryRepository;

@Service
public class InventoryService {
	private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    
    //Find Inventory By Product Id
    public Inventory getInventoryByProductId(Long productId) {

        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new InventoryNotFoundException(
                                "Inventory not found for product id: "
                                        + productId));
    }
    
    //Reduce Stock
    public void reduceStock(
            Long productId,
            Integer requestedQuantity) {

        Inventory inventory =
                getInventoryByProductId(productId);

        if (inventory.getAvailableQuantity()
                < requestedQuantity) {

            throw new InsufficientStockException(
                    "Insufficient stock for product id: "
                            + productId);
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity()
                        - requestedQuantity);

        inventory.setLastUpdatedAt(
                LocalDateTime.now());

        inventoryRepository.save(inventory);
    }
    
    
    public InventoryResponse createInventory(
            InventoryRequest request) {

        Inventory inventory = new Inventory();

        inventory.setProductId(request.getProductId());
        inventory.setAvailableQuantity(
                request.getAvailableQuantity());
        inventory.setLastUpdatedAt(
                LocalDateTime.now());

        Inventory savedInventory =
                inventoryRepository.save(inventory);

        return mapToResponse(savedInventory);
    }

    public InventoryResponse getInventory(Long id) {

        Inventory inventory =
                inventoryRepository.findById(id)
                        .orElseThrow(() ->
                                new InventoryNotFoundException(
                                        "Inventory not found with id: "+id));

        return mapToResponse(inventory);
    }

    public List<InventoryResponse> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private InventoryResponse mapToResponse(
            Inventory inventory) {

        return new InventoryResponse(
                inventory.getId(),
                inventory.getProductId(),
                inventory.getAvailableQuantity(),
                inventory.getLastUpdatedAt()
        );
    }
    
    public InventoryResponse updateInventory(
            Long id,
            InventoryRequest request) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new InventoryNotFoundException(
                                "Inventory not found with id: " + id));

        inventory.setProductId(request.getProductId());
        inventory.setAvailableQuantity(request.getAvailableQuantity());
        inventory.setLastUpdatedAt(LocalDateTime.now());

        Inventory updatedInventory =
                inventoryRepository.save(inventory);

        return mapToResponse(updatedInventory);
    }
    
    public void deleteInventory(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new InventoryNotFoundException(
                                "Inventory not found with id: " + id));

        inventoryRepository.delete(inventory);
    }
    
    public void validateStock(
            Long productId,
            Integer requestedQuantity) {

        Inventory inventory =
                inventoryRepository.findByProductId(productId)
                        .orElseThrow(() ->
                                new InventoryNotFoundException(
                                        "Inventory not found"));

        if (inventory.getAvailableQuantity()
                < requestedQuantity) {

            throw new InsufficientStockException(
                    "Insufficient stock");
        }
    }
}
