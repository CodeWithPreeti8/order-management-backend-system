package com.example.Order.Management.Backend.System.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Order.Management.Backend.System.inventory.dto.InventoryRequest;
import com.example.Order.Management.Backend.System.inventory.dto.InventoryResponse;
import com.example.Order.Management.Backend.System.inventory.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
@Tag(
        name = "Inventory APIs",
        description = "Operations related to inventory management"
)
public class InventoryController {
	
	private final InventoryService inventoryService;

    public InventoryController(
            InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    @PostMapping
    @Operation(
            summary = "Create Inventory",
            description = "Creates inventory for a product"
    )
    public InventoryResponse createInventory(
            @Valid @RequestBody InventoryRequest request) {

        return inventoryService.createInventory(request);
    }

    @Operation(
            summary = "Get Inventory By Product Id",
            description = "Fetch inventory details using product id"
    )
    @GetMapping("/{id}")
    public InventoryResponse getInventory(
            @PathVariable Long id) {

        return inventoryService.getInventory(id);
    }

    @Operation(
            summary = "Get all Inventory ",
            description = "Fetch all inventory details "
    )
    @GetMapping
    public List<InventoryResponse> getAllInventory() {

        return inventoryService.getAllInventory();
    }
    
    
    @Operation(
            summary = "Update Inventory",
            description = "Updates available quantity for a product"
    )
    @PutMapping("/{id}")
    public InventoryResponse updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequest request) {

        return inventoryService.updateInventory(id, request);
    }
    
    @Operation(
            summary = "Delete Inventory",
            description = "Deletes inventory details for a specific product"
    )
    @DeleteMapping("/{id}")
    public String deleteInventory(
            @PathVariable Long id) {

        inventoryService.deleteInventory(id);

        return "Inventory deleted successfully";
        
    }
}
