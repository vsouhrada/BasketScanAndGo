# Basket Server

## Overview

The Basket Server is a Ktor-based backend application for the Basket Scan & Go system. It provides RESTful API endpoints for user authentication, basket management, product retrieval, store information, and user management.

## Architecture

The Basket Server follows a modular architecture using Ktor's plugin-based system. The server is organized into the following components:

```
┌─────────────────────────────────────────────────────────────────┐
│                       APPLICATION ENTRY                         │
│                                                                 │
│                       Application.kt                            │
└───────────────────────────────┬─────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                          KTOR PLUGINS                           │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │      HTTP       │  │    Routing      │  │  Serialization  │  │
│  │   (CORS, etc)   │  │                 │  │     (JSON)      │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │    Security     │  │   Monitoring    │  │   Exception     │  │
│  │  (JWT Auth)     │  │    (Logging)    │  │    Handling     │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└───────────────────────────────┬─────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                         ROUTE HANDLERS                          │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   AuthRoutes    │  │  BasketRoutes   │  │  ProductRoutes  │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐                       │
│  │   StoreRoutes   │  │   UserRoutes    │                       │
│  └─────────────────┘  └─────────────────┘                       │
└───────────────────────────────┬─────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                          DATA MODELS                            │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │    BasketDto    │  │     Product     │  │      Store      │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
│                                                                 │
│  ┌─────────────────┐                                            │
│  │      User       │                                            │
│  └─────────────────┘                                            │
└─────────────────────────────────────────────────────────────────┘
```

### Component Descriptions

#### Application Entry
The `Application.kt` file serves as the entry point for the server. It configures the Netty engine and sets up all the necessary Ktor plugins.

#### Ktor Plugins
- **HTTP**: Configures CORS and other HTTP-related settings
- **Routing**: Sets up the API routes
- **Serialization**: Configures JSON serialization using kotlinx.serialization
- **Security**: Implements JWT authentication
- **Monitoring**: Sets up logging and monitoring
- **Exception Handling**: Manages error responses

#### Route Handlers
- **AuthRoutes**: Handles user authentication and token management
- **BasketRoutes**: Manages shopping baskets
- **ProductRoutes**: Provides product information
- **StoreRoutes**: Provides store information
- **UserRoutes**: Manages user data

#### Data Models
- **BasketDto**: Represents a shopping basket
- **Product**: Represents a product
- **Store**: Represents a store
- **User**: Represents a user

## API Endpoints

### Authentication

```
POST /auth/login
```
Authenticates a user and returns an access token.

**Request Body**:
```json
{
  "username": "string",
  "password": "string"
}
```

**Response**:
```json
{
  "token": "string",
  "refreshToken": "string"
}
```

```
POST /auth/refresh
```
Refreshes an access token.

**Request Body**:
```json
{
  "refreshToken": "string"
}
```

**Response**:
```json
{
  "token": "string",
  "refreshToken": "string"
}
```

### Baskets

```
POST /baskets
```
Creates a new basket.

**Request Body**:
```json
{
  "userId": "string",
  "storeId": "string"
}
```

**Response**:
```json
{
  "id": "string",
  "userId": "string",
  "storeId": "string",
  "items": [],
  "createdAt": "string",
  "updatedAt": "string"
}
```

```
GET /baskets/{id}
```
Gets a basket by ID.

**Response**:
```json
{
  "id": "string",
  "userId": "string",
  "storeId": "string",
  "items": [
    {
      "productId": "string",
      "quantity": "number",
      "price": "number"
    }
  ],
  "createdAt": "string",
  "updatedAt": "string"
}
```

```
POST /baskets/{id}/items
```
Adds an item to a basket.

**Request Body**:
```json
{
  "productId": "string",
  "quantity": "number"
}
```

**Response**:
```json
{
  "id": "string",
  "userId": "string",
  "storeId": "string",
  "items": [
    {
      "productId": "string",
      "quantity": "number",
      "price": "number"
    }
  ],
  "createdAt": "string",
  "updatedAt": "string"
}
```

### Products

```
GET /products
```
Gets all products.

**Response**:
```json
[
  {
    "id": "string",
    "name": "string",
    "description": "string",
    "price": "number",
    "imageUrl": "string"
  }
]
```

```
GET /products/{id}
```
Gets a product by ID.

**Response**:
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "price": "number",
  "imageUrl": "string"
}
```

### Stores

```
GET /stores
```
Gets all stores.

**Response**:
```json
[
  {
    "id": "string",
    "name": "string",
    "address": "string",
    "location": {
      "latitude": "number",
      "longitude": "number"
    }
  }
]
```

```
GET /stores/{id}
```
Gets a store by ID.

**Response**:
```json
{
  "id": "string",
  "name": "string",
  "address": "string",
  "location": {
    "latitude": "number",
    "longitude": "number"
  }
}
```

### Users

```
GET /users
```
Gets all users.

**Response**:
```json
[
  {
    "id": "string",
    "username": "string",
    "email": "string",
    "firstName": "string",
    "lastName": "string"
  }
]
```

```
GET /users/{id}
```
Gets a user by ID.

**Response**:
```json
{
  "id": "string",
  "username": "string",
  "email": "string",
  "firstName": "string",
  "lastName": "string"
}
```

## Technologies Used

The Basket Server is built using the following technologies:

```
┌─────────────────────────────────────────────────────────────────┐
│                                                                 │
│                         BASKET SERVER                           │
│                                                                 │
└───────────────────────────────┬─────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                         CORE TECHNOLOGIES                       │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │      Kotlin     │  │      Ktor       │  │     Netty       │  │
│  │    (2.1.10)     │  │     (3.1.1)     │  │     Engine      │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└───────────────────────────────┬─────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                       SUPPORTING LIBRARIES                      │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │     Kotlinx     │  │     Kotlinx     │  │     Kotlinx     │  │
│  │  Serialization  │  │   Coroutines    │  │    Datetime     │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐                       │
│  │    Logback      │  │    Auth0 JWT    │                       │
│  │    Classic      │  │                 │                       │
│  └─────────────────┘  └─────────────────┘                       │
└─────────────────────────────────────────────────────────────────┘
```

## Data Storage

Currently, the server uses JSON files for data storage:

- `product.json`: Product data
- `stores.json`: Store data
- `users.json`: User data

In a production environment, this would be replaced with a database.

## Running the Server

To run the server, use the following command:

```bash
./gradlew :server:basket-server:run
```

The server will start on port 8080 by default.

## Configuration

The server configuration is stored in `src/main/resources/application.conf`. You can modify this file to change the server port, JWT settings, etc.

## Future Improvements

Potential future improvements for the Basket Server include:

1. **Database Integration**: Replace JSON file storage with a proper database
2. **Caching**: Implement caching for frequently accessed data
3. **Rate Limiting**: Add rate limiting to prevent abuse
4. **Enhanced Security**: Implement additional security measures
5. **API Versioning**: Add support for API versioning
6. **Metrics and Monitoring**: Implement more comprehensive monitoring
7. **Containerization**: Provide Docker support for easier deployment
