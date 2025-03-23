# Basket Server

A Ktor-based server for the Basket Scan & Go application.

## Overview

This server provides the backend API for the Basket Scan & Go application. It includes endpoints for user authentication, basket management, product retrieval, store information, and user management.

## Technologies Used

- Kotlin
- Ktor
- Kotlinx Serialization
- JWT Authentication

## API Endpoints

### Authentication

- `POST /auth/login`: Authenticate a user and get access token
- `POST /auth/refresh`: Refresh an access token

### Baskets

- `POST /baskets`: Create a new basket
- `GET /baskets/{id}`: Get a basket by ID
- `POST /baskets/{id}/items`: Add an item to a basket

### Products

- `GET /products`: Get all products
- `GET /products/{id}`: Get a product by ID

### Stores

- `GET /stores`: Get all stores
- `GET /stores/{id}`: Get a store by ID

### Users

- `GET /users`: Get all users
- `GET /users/{id}`: Get a user by ID

## Running the Server

To run the server, use the following command:

```bash
./gradlew :basket-server:run
```

The server will start on port 8080 by default.

## Configuration

The server configuration is stored in `src/main/resources/application.conf`. You can modify this file to change the server port, JWT settings, etc.

## Data

The server uses JSON files in the `server/resources` directory for data storage:

- `product.json`: Product data
- `stores.json`: Store data
- `users.json`: User data

In a production environment, this would be replaced with a database.
