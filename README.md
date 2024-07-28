# Online Store Project

## Overview
This project is an online store application designed to demonstrate fundamental and advanced backend concepts. The project is built using microservices architecture, incorporating various services like Category-service, Inventory-service, and Product-service. It also utilizes messaging queues (Kafka), an API gateway, and security through the Keycloak server.

## Key Features
- **Microservices Architecture**: The project is divided into several microservices, each responsible for specific functionality.
- **Messaging Queue (Kafka)**: Used for efficient communication between microservices.
- **API Gateway**: Centralized entry point for managing and routing requests to the appropriate microservices.
- **Security with Keycloak**: Provides authentication and authorization services to secure the application.

## Services

### Category-service
- Manages product categories.
- Allows CRUD operations on categories.
- Ensures proper categorization of products.

### Inventory-service
- Manages inventory levels.
- Tracks stock availability and updates inventory based on sales and restocks.
- Provides inventory-related information to other services.

### Product-service
- Manages product details.
- Allows CRUD operations on products.
- Integrates with Category-service for category information and Inventory-service for stock details.

## Technologies Used
- **Spring Boot**: For building the microservices.
- **Apache Kafka**: For messaging and communication between services.
- **Spring Cloud Gateway**: For API gateway implementation.
- **Keycloak**: For authentication and authorization.
- **Docker**: For containerization of microservices.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Docker
- Docker Compose
- Maven

### Installation and Running

1. **Clone the repository**:
   ```bash
   git clone https://github.com/HazemEw/Online-Store-Microservice.git
