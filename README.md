# Distributed Task Processing Platform

## 📌 Overview
This project is a distributed task processing system built using **Spring Boot**, **RabbitMQ**, and **MySQL**.  
It demonstrates asynchronous processing, fault tolerance, and scalable architecture.

---

## 🏗️ Architecture

- **API Service**
  - Accepts task requests
  - Stores tasks in DB
  - Uses Outbox pattern to publish events

- **Worker Service**
  - Consumes tasks from RabbitMQ
  - Processes tasks asynchronously
  - Handles retries, failures, and logging

- **RabbitMQ**
  - Message broker for async communication
  - Supports retry queue and dead letter queue (DLQ)

---

## 🔄 Flow

```
Client → API → DB → Outbox → RabbitMQ → Worker → DB
                                      ↓
                                 Retry Queue
                                      ↓
                                    DLQ
```

---

## ⚙️ Features

### ✅ Core Features
- Asynchronous task processing using RabbitMQ
- Outbox pattern for reliable message publishing
- Separate API and Worker services

### 🔁 Reliability
- Retry mechanism with retry count
- Delayed retry using TTL queue
- Dead Letter Queue (DLQ) for failed tasks
- Idempotent processing using DB locking

### 🌐 API Features
- Create task API
- Get task by ID
- Pagination support
- Filter by status

### 🛡️ Error Handling
- Global exception handling
- Validation support
- Clean error responses

---

## 🧪 API Endpoints

### Create Task
```
POST /tasks
```

### Get Task by ID
```
GET /tasks/{id}
```

### Get All Tasks
```
GET /tasks
```

### Pagination
```
GET /tasks/paged?page=0&size=10
```

### Filter by Status
```
GET /tasks/status/FAILED
```

### Filter + Pagination
```
GET /tasks/status/paged?status=FAILED&page=0&size=10
```

---

## 🧰 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- RabbitMQ
- MySQL
- Gradle

---

## 🚀 How to Run

### 1. Start Dependencies
- MySQL
- RabbitMQ

### 2. Run Services

```bash
cd api-service
./gradlew bootRun

cd worker-service
./gradlew bootRun
```

---

## 📊 Key Concepts Demonstrated

- Distributed system design
- Event-driven architecture
- Retry & failure handling
- Dead Letter Queue (DLQ)
- Idempotency
- Pagination & filtering
- Exception handling

---

## 💡 Future Improvements (Optional)

- Docker setup
- API Gateway
- JWT Authentication
- Monitoring (Prometheus/Grafana)
- Flyway migrations

---
<!--
## 👨‍💻 Author
Pushpak A. Fasate
-->