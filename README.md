# Distributed Task Processing Platform

A distributed backend system built with **Spring Boot, RabbitMQ, and MySQL** that processes background tasks asynchronously.

## Architecture

Client → API Service → RabbitMQ → Worker Service → Database

## Technologies

- Java 21
- Spring Boot
- RabbitMQ
- MySQL
- Docker
- Gradle

## Services

### API Service
Handles task creation and publishing tasks to the message queue.

### Worker Service
Consumes tasks from the queue and processes them asynchronously.