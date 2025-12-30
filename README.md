# Expense Tracker REST API

## Overview
A backend RESTful application built using Spring Boot that enables users to
manage expenses and budgets. The application provides clean APIs for creating,
retrieving, updating, and deleting expense records with proper validation and
error handling.

## Problem Statement
Tracking personal or business expenses manually can be inefficient and
error-prone. This project demonstrates how a structured backend service can
centralize expense management and enforce budget-related rules using a clean
API-driven approach.

## Solution
The application exposes REST APIs that allow clients to:
- Manage expense records
- Define and track budgets
- Validate data consistently
- Handle errors using centralized exception handling

## Architecture
The project follows a layered architecture:
- **Controller Layer** – Handles HTTP requests and responses
- **Service Layer** – Implements business logic
- **Repository Layer** – Manages database operations using JPA
- **Model Layer** – Represents domain entities
- **Exception Layer** – Centralized exception handling

## Tech Stack
- **Language:** Java
- **Framework:** Spring Boot
- **API Style:** RESTful APIs
- **Persistence:** Spring Data JPA
- **Build Tool:** Maven
- **Version Control:** Git

## Key Features
- CRUD operations for expenses
- Budget management support
- Clean separation of concerns
- Global exception handling
- Scalable backend design

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/beemaneni-sandya/Expense-Tracker.git
2. Navigate to the project directory:
   ```bash
   cd Expense-Tracker
3. Run the application:
   ```bash
   mvn spring-boot:run
4. Access the APIs at:
   ```bash
   http://localhost:8080
**Sample Endpoints**
- POST /Expenses
- GET /expenses
- PUT /expenses/{id}
- DELETE /expenses/{id}
- POST /budgets
- GET /budgets

## Future Enhancements
- Authentication and authorization
- User-specific expense tracking
- Reporting and analytics
- Frontend integration
