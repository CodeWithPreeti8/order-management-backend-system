# Order Management Backend System

## Overview

A backend Order Management System built using Spring Boot.

The application allows users to manage Products, Inventory, and Orders while demonstrating modern backend development concepts such as Event-Driven Architecture with Kafka, Redis Caching, Swagger API Documentation, Unit Testing, and Docker.

This project was built as a hands-on learning project to gain practical experience with backend development technologies commonly used in enterprise applications.

---

## Features

### Product Management
- Create Product
- Update Product
- Delete Product
- Get Product By Id
- Get All Products

### Inventory Management
- Add Inventory
- Update Inventory
- Delete Inventory
- Validate Stock Availability

### Order Management
- Create Order
- Get Order By Id
- Get All Orders
- Update Order Status

### Event Driven Architecture
- Kafka Producer
- Kafka Consumer
- Order Created Event
- Order Status Event

### Performance Optimization
- Redis Caching

### API Documentation
- Swagger OpenAPI

### Testing
- JUnit 5
- Mockito

### DevOps & Version Control
- Docker
- Git
- GitHub

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Apache Kafka
- Redis
- Swagger OpenAPI
- JUnit 5
- Mockito
- Maven
- Docker
- Git & GitHub

---

## Project Modules

### Product Module
Responsible for managing products.

Operations:
- Create Product
- Update Product
- Delete Product
- Get Product By Id
- Get All Products

### Inventory Module
Responsible for managing stock.

Operations:
- Add Inventory
- Update Inventory
- Delete Inventory
- Validate Product Stock

### Order Module
Responsible for order processing.

Operations:
- Create Order
- Get Order Details
- Get All Orders
- Update Order Status

---

## Event Driven Architecture Using Kafka

Kafka is used for asynchronous communication between services.

### Order Creation Flow

1. User creates an order.
2. Order Service validates inventory availability.
3. Order is saved in database.
4. OrderCreatedEvent is published to Kafka Topic.
5. Inventory Consumer consumes the event.
6. Inventory stock is reduced asynchronously.

### Benefits

- Loose Coupling
- Better Scalability
- Asynchronous Processing
- Event Driven Design

---

## Redis Caching

Redis is used to improve API performance.

Implemented Cache:

- Get Product By Id
- Get All Products

### Cache Flow

First Request:

Client → Spring Boot → MySQL → Redis Cache

Subsequent Requests:

Client → Redis Cache

### Benefits

- Faster Response Time
- Reduced Database Calls
- Improved Performance

---

## API Documentation

Swagger OpenAPI has been integrated for API testing and documentation.

Swagger URL:

http://localhost:8080/swagger-ui/index.html

---

## Unit Testing

Implemented Unit Testing using:

- JUnit 5
- Mockito

Test Coverage Includes:

- Successful Order Creation
- Product Not Found Scenario
- Invalid Quantity Validation
- Service Layer Testing

---

## Database

Database Used:

- MySQL

Tables:

- products
- inventory
- orders
- order_items

---

## Docker Setup

Services Running Through Docker:

### MySQL
Port: 3306

### Kafka
Port: 9092

### Redis
Port: 6379

---

## Run Project Locally

### Clone Repository

```bash
git clone https://github.com/CodeWithPreeti8/order-management-backend-system.git
```

### Navigate To Project

```bash
cd order-management-backend-system
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Learning Outcomes

Through this project I gained hands-on experience with:

- Spring Boot REST APIs
- JPA and Hibernate
- DTO Pattern
- Exception Handling
- Kafka Event Driven Architecture
- Redis Caching
- Swagger Documentation
- Unit Testing with JUnit and Mockito
- Docker Containerization
- Git and GitHub

---

## Future Enhancements

- Spring Security with JWT Authentication
- Role Based Authorization
- API Gateway
- Microservices Architecture
- CI/CD Pipeline
- Kubernetes Deployment

---

## Author

Preeti Singh

Backend Developer | Java | Spring Boot | Kafka | Redis | MySQL