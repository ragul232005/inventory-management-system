# Inventory Management System - Project Structure

## Overview
This project is organized with a clear separation between frontend and backend, allowing independent development and deployment.

---

## Frontend Structure

```
inventory-frontend/
├── index.html                    # Main HTML entry point
├── js/
│   └── app.js                   # Main application logic (API calls, event handlers, WebSocket)
├── css/
│   └── styles.css               # Application styling
└── assets/                      # Reserved for images, icons, fonts (future use)
```

### Frontend Files
- **index.html**: Main HTML page with form inputs and product table. References CSS and JS files.
- **js/app.js**: Contains all JavaScript functionality including:
  - API calls to backend (`GET /api/products`, `POST /api/products`, `DELETE /api/products/{id}`)
  - WebSocket connection for real-time updates
  - UI rendering and form handling
- **css/styles.css**: Styling for the UI

### Running Frontend Development
The frontend is served by the backend Spring Boot application. Open `http://localhost:8080/` after starting the backend.

---

## Backend Structure

```
inventory-backend/
├── pom.xml                              # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/com/example/inventory/
│   │   │   ├── InventoryApplication.java        # Spring Boot entry point & data seeding
│   │   │   ├── config/
│   │   │   │   └── WebSocketConfig.java         # WebSocket setup (STOMP messaging)
│   │   │   ├── controller/
│   │   │   │   ├── ProductController.java       # REST API for products (CRUD)
│   │   │   │   └── CategoryController.java      # REST API for categories (CRUD)
│   │   │   ├── service/
│   │   │   │   └── ProductService.java          # Business logic & WebSocket broadcasting
│   │   │   ├── model/
│   │   │   │   ├── Product.java                 # JPA entity for products
│   │   │   │   └── Category.java                # JPA entity for categories
│   │   │   └── repository/
│   │   │       ├── ProductRepository.java       # JPA repository for products
│   │   │       └── CategoryRepository.java      # JPA repository for categories
│   │   └── resources/
│   │       ├── application.properties            # Spring Boot configuration
│   │       └── static/                           # Served static files (built from frontend)
│   │           ├── index.html
│   │           ├── app.js
│   │           └── styles.css
│   └── test/                                    # Unit tests (if any)
└── target/
    └── inventory-0.0.1-SNAPSHOT.jar             # Built JAR application
```

### Backend Layers
- **Controller**: REST endpoints managing HTTP requests (`/api/products`, `/api/categories`)
- **Service**: Business logic and data manipulation (includes WebSocket broadcasting)
- **Repository**: Data access layer (JPA queries)
- **Model**: JPA entities (Product, Category)
- **Config**: Spring Boot configuration (WebSocket setup)

### API Endpoints
- `GET /api/products` - Fetch all products
- `GET /api/products/{id}` - Fetch a product by ID
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a product
- `DELETE /api/products/{id}` - Delete a product
- `GET /api/categories` - Fetch all categories
- `POST /api/categories` - Create a new category
- `PUT /api/categories/{id}` - Update a category
- `DELETE /api/categories/{id}` - Delete a category

### WebSocket Endpoint
- `WS /ws` - STOMP WebSocket endpoint for real-time product updates
  - Topic: `/topic/products` - Broadcasts product changes
  - Topic: `/topic/products/delete` - Broadcasts product deletions

---

## Building & Running

### Prerequisites
- Java 17+ (or 22 as installed)
- Maven 3.9+

### Build Backend
```bash
cd inventory-backend
mvn clean package -DskipTests
```

### Run Backend
```bash
java -jar inventory-backend/target/inventory-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080/`

---

## Database
- **H2 Database** (in-memory)
- URL: `http://localhost:8080/h2-console` (optional - requires password: empty)
- Tables: `products`, `categories`

---

## Notes
- Frontend files are served as static assets from the backend's `static/` folder
- For development, if you modify frontend files in `inventory-frontend/`, copy them to `inventory-backend/src/main/resources/static/` before rebuilding
- The WebSocket keeps the frontend in sync with backend changes in real-time
