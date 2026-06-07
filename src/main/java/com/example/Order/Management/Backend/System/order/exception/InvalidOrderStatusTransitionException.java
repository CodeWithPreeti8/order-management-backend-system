package com.example.Order.Management.Backend.System.order.exception;

public class InvalidOrderStatusTransitionException extends RuntimeException {
	
	public InvalidOrderStatusTransitionException(
            String message) {
        super(message);
    }

}
