## Task-Manager
Task Manager - is a sample project designed for task management.This application offers basic CRUD functionality through a RESTful API and features a simple web interface for demonstration. All API endpoints are protected with JWT (JSON Web Tokens) authentication.

## Technologies Used
- Java 17  
- PostgreSQL (latest)  
- Spring Boot 3.5.0
- Liquibase 2.8.8

## Database Initialization
All database schemas, tables, and even a test user are automatically created on the first application startup thanks to Liquibase.

## Configure Spring Datasource, JPA, App properties
Open ```src/main/resources/application.yaml```    

For PostgreSQL:
```
spring:
  application:
    name:
      task-manager

  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5433/task_manager   # Replace with your database host, port, and name
    username: postgres                                   # Replace with your database username
    password: pass                                       # Replace with your database password

jwt:
  accessTokenExpiration: 900000          # Access token lifetime in milliseconds (here, 15 minutes)
  refreshTokenExpiration: 604800000      # Refresh token lifetime in milliseconds (here, 7 days)
  secret: snksiwijhwjwouwwwowiwoiwuwuakjwwjauqo  # Replace with your own secret key for JWT

```

## Run Spring Boot application
``` ./gradlew bootRun```  
