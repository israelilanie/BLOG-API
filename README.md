# Blog API (Spring Boot)

Production-oriented REST API for a blog platform, built to demonstrate backend fundamentals recruiters look for: authentication, authorization, layered architecture, validation, error handling, persistence, and deployment readiness.

---

## Live Deployment

The API is deployed and publicly accessible at:

- Base URL: **https://blog-api-a9iq.onrender.com**

Useful deployed endpoints:

- Swagger UI: `https://blog-api-a9iq.onrender.com/swagger-ui.html`
- OpenAPI JSON: `https://blog-api-a9iq.onrender.com/v3/api-docs`
- Health: `https://blog-api-a9iq.onrender.com/actuator/health`

## Why this project matters

This project is designed to show practical backend engineering skills, not just CRUD:

- Secure user authentication with JWT.
- Role-based and ownership-based authorization.
- Clean architecture (Controller → Service → Repository).
- DTO-based API boundaries.
- Pagination-ready endpoints.
- Global exception handling.
- Environment-aware configuration (`dev` / `prod`).
- Dockerized deployment path.

---

## Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data JPA**
- **PostgreSQL** (production)
- **H2** (development)
- **Spring Mail**
- **Springdoc OpenAPI / Swagger UI**
- **Maven**
- **Docker**

---

## Architecture

```text
Controller  ->  Service  ->  Repository  ->  Database
   |              |             |
 HTTP/API      Business      Data access
 contracts      rules         + queries
```

### Layer responsibilities

- **Controller**: request mapping, validation entry, HTTP response.
- **Service**: business logic, authorization decisions, orchestration.
- **Repository**: persistence and query operations.
- **Mapper/DTO**: separates API contract from internal entities.

---

## Security Model

### Authentication

- Login returns a JWT.
- Protected endpoints require:

```http
Authorization: Bearer <token>
```

### Authorization

- Roles:
  - `ROLE_USER`
  - `ROLE_ADMIN`
- Access is enforced by both:
  - Route security configuration.
  - Ownership checks in business logic (e.g., edit/delete own content).

---

## Feature Overview

### Users

- Register account
- Login
- Read/update/delete own profile (`/users/me`)
- Admin-only user management endpoints

### Posts

- Create post
- Read single/all posts
- Update/delete own post
- Read own posts (`/post/me/page`, `/post/me/list`)
- Pagination support

### Comments

- Create comment on a post
- Read comments by post
- Update/delete own comment
- Pagination support

### Likes

- Like/unlike posts
- Like/unlike comments
- One-like-per-user-per-entity behavior

---

## API Documentation

When the app is running:

- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v3/api-docs`
- Health endpoint: `/actuator/health`

---

## Local Development

### 1) Prerequisites

- Java 21
- Maven 3.9+

### 2) Run with default profile (`dev`)

```bash
./mvnw spring-boot:run
```

By default, the app uses in-memory H2 from `application-dev.properties`.

### 3) Run tests

```bash
./mvnw test
```

---

## Environment Variables

Set these for production profile:

- `SPRING_PROFILES_ACTIVE=prod`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_DURATION`
- `ADMIN_EMAIL`
- `ADMIN_PASSWORD`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`

---

## Docker

Build image:

```bash
docker build -t blog-api .
```

Run container:

```bash
docker run -p 8080:8080 blog-api
```

---

## Recruiter Notes (Project Intent)

This project intentionally demonstrates:

- backend API design with layered architecture,
- authentication and access control,
- practical validation and exception patterns,
- environment-based configuration for deployment,
- maintainable structure for feature growth.

If you want a guided walkthrough, start from:

1. `SecurityConfig` + `JwtFilter` + `JwtUtil` (security flow)
2. `UserService`, `PostService`, `CommentService` (business rules)
3. `GlobalExceptionHandler` (error contract)

---

## Next planned improvements

- Add comprehensive unit/integration test coverage.
- Add CI pipeline (build + tests).
- Add DB migrations (Flyway).
- Add API examples collection (Postman).

