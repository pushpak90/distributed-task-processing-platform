
# 🚀 Distributed Task Processing Platform

## 📌 Overview
A **production-grade distributed task processing system** built using **Spring Boot, RabbitMQ, and MySQL**.  
This project demonstrates **asynchronous processing, fault tolerance, scalability, and reliable messaging** using modern microservices architecture.

---

## 🏗️ System Architecture

### 🔹 API Service
- Accepts client task requests
- Persists tasks in MySQL
- Implements **Outbox Pattern** for reliable event publishing

### 🔹 Worker Service
- Consumes messages from RabbitMQ
- Processes tasks asynchronously
- Handles retries, failures, and logging

### 🔹 Message Broker (RabbitMQ)
- Enables async communication between services
- Supports:
  - Retry Queues
  - Dead Letter Queue (DLQ)

---

## 🔄 System Flow

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
- Asynchronous task execution using RabbitMQ
- Microservices-based architecture
- Reliable event publishing using Outbox Pattern

### 🔁 Reliability & Fault Tolerance
- Retry mechanism with configurable retry count
- Delayed retries using TTL queues
- Dead Letter Queue (DLQ) for failed tasks
- Idempotent processing using database locking

### 🌐 API Capabilities
- Create and manage tasks
- Fetch task by ID
- Pagination support
- Filter tasks by status

### 🛡️ Error Handling
- Global exception handling
- Input validation
- Structured error responses

---

## 🧪 API Endpoints

| Feature | Endpoint |
|--------|---------|
| Create Task | `POST /tasks` |
| Get Task by ID | `GET /tasks/{id}` |
| Get All Tasks | `GET /tasks` |
| Pagination | `GET /tasks/paged?page=0&size=10` |
| Filter by Status | `GET /tasks/status/{status}` |
| Filter + Pagination | `GET /tasks/status/paged?status=FAILED&page=0&size=10` |

---

## 🧰 Tech Stack

- **Language:** Java 21  
- **Backend:** Spring Boot  
- **Database:** MySQL  
- **Messaging:** RabbitMQ  
- **ORM:** Spring Data JPA  
- **Build Tool:** Gradle  

---

## 🚀 Getting Started

### 🔹 Prerequisites
- Java 21+
- MySQL
- RabbitMQ

### 🔹 Run the Application

```bash
# Start API Service
cd api-service
./gradlew bootRun

# Start Worker Service
cd worker-service
./gradlew bootRun
```

---

## 📊 Key Concepts Demonstrated

- Distributed Systems Design
- Event-Driven Architecture
- Outbox Pattern
- Retry Mechanisms & Failure Handling
- Dead Letter Queue (DLQ)
- Idempotent Processing
- Pagination & Filtering
- Exception Handling

---

## 🔮 Future Enhancements

- Docker & Containerization
- API Gateway Integration
- JWT Authentication & Security
- Monitoring (Prometheus, Grafana)
- Database Migration (Flyway)

---

## ⭐ Why This Project Matters

This project simulates **real-world backend system design**, showcasing:
- Scalability through asynchronous processing
- Reliability using messaging patterns
- Clean microservices architecture

---

## 👨‍💻 Author
**Pushpak A. Fasate**
