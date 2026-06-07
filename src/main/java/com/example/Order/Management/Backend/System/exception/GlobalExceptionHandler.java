package com.example.Order.Management.Backend.System.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Order.Management.Backend.System.common.dto.ErrorResponse;
import com.example.Order.Management.Backend.System.inventory.exception.InsufficientStockException;
import com.example.Order.Management.Backend.System.inventory.exception.InventoryNotFoundException;
import com.example.Order.Management.Backend.System.order.exception.InvalidOrderStatusTransitionException;
import com.example.Order.Management.Backend.System.order.exception.InvalidQuantityException;
import com.example.Order.Management.Backend.System.order.exception.OrderNotFoundException;
import com.example.Order.Management.Backend.System.order.exception.ProductNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFoundException(
            ProductNotFoundException ex) {

		ErrorResponse errorResponse =
	            new ErrorResponse(
	                    ex.getMessage(),
	                    HttpStatus.NOT_FOUND.value(),
	                    LocalDateTime.now()
	            );
		return new ResponseEntity<>(
	            errorResponse,
	            HttpStatus.NOT_FOUND
	    );

    }
	
	@ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidQuantityException(
            InvalidQuantityException ex) {

		ErrorResponse errorResponse =
	            new ErrorResponse(
	                    ex.getMessage(),
	                    HttpStatus.BAD_REQUEST.value(),
	                    LocalDateTime.now()
	            );
		return new ResponseEntity<>(
	            errorResponse,
	            HttpStatus.BAD_REQUEST
	    );
    }
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorResponse>
	handleOrderNotFoundException(
	        OrderNotFoundException ex) {

	    ErrorResponse errorResponse =
	            new ErrorResponse(
	                    ex.getMessage(),
	                    HttpStatus.NOT_FOUND.value(),
	                    LocalDateTime.now()
	            );

	    return new ResponseEntity<>(
	            errorResponse,
	            HttpStatus.NOT_FOUND
	    );
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InventoryNotFoundException.class)
	public ResponseEntity<String> handleInventoryNotFoundException(
	        InventoryNotFoundException ex) {

	    return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(ex.getMessage());
	}
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<String> handleInsufficientStockException(
	        InsufficientStockException ex) {

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(ex.getMessage());
	}
	
	@ExceptionHandler(
	        InvalidOrderStatusTransitionException.class)
	public ResponseEntity<String>
	handleInvalidOrderStatusTransitionException(
	        InvalidOrderStatusTransitionException ex) {

	    return ResponseEntity.badRequest()
	            .body(ex.getMessage());
	}
}
