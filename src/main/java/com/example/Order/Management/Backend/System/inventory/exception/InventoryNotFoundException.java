package com.example.Order.Management.Backend.System.inventory.exception;

public class InventoryNotFoundException extends RuntimeException{

	public InventoryNotFoundException(String message) {
		super(message);
	}
}
