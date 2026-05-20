**🧠 Blog API — Spring Boot Backend**

A production-ready REST API for a blog system built with Spring Boot, featuring authentication, role-based access control, pagination, comments, likes, and deployed on Render with PostgreSQL. 

 

**🚀 Live Deployment**


Base URL: https://blog-api-a9iq.onrender.com  
Swagger UI: /swagger-ui.html  
Actuator Health: /actuator/health  

 

**🛠 Tech Stack**


Java 21  
Spring Boot  
Spring Security + JWT  
Spring Data JPA  
PostgreSQL (Production)  
H2 Database (Development)  
Docker  
Maven  
Spring Mail
Render (Deployment)  

 

**🧱 Architecture**


Controller → Service → Repository pattern  
DTO-based request/response layer  
Global exception handling  
Role-based authorization (USER / ADMIN)  
Environment-based configuration (dev / prod)  

 

**🔐 Authentication & Authorization**


**Roles:**

ROLE_USER  
ROLE_ADMIN  

**Security Features:**

JWT-based authentication  
Password encryption (BCrypt)  
Method-level authorization  
Ownership-based access control  

 

**👤 User Features**


**Public :**

Register  
Login  

**Authenticated User**

Get profile (/users/me)  
Update profile  
Delete account  

**Admin**

Get all users (pagination)  
Get user by ID  
Delete user by ID  

 

**📝 Post Features**


**Public**

Get all posts (pagination)  
Get post by ID  
Get posts by user ID  

**Authenticated User**

Create post  
Update own post  
Delete own post  
Get own posts (/posts/me)  

**Admin** 

Full access to all posts  

 

**💬 Comment Features**


**Public**

Get comments by post ID  

**Authenticated User**

Create comment  
Update own comment  
Delete own comment  

**Admin**

Moderate all comments  

 

**❤️ Like System**


Users can like posts  
Users can like comments  
One like per user per entity  
Toggle like/unlike system  

 

**📦 Pagination & Filtering**


Pageable support for:  

Users  
Posts  
Sorting enabled  
Scalable query structure  

 

**⚙️ Environment Variables**


Required: 

SPRING_PROFILES_ACTIVE=prod 
 
DB_URL=jdbc:postgresql://... 
DB_USERNAME=... 
DB_PASSWORD=... 
 
JWT_SECRET=your-secret 
JWT_DURATION=86400000 
 
ADMIN_EMAIL=admin@example.com 
ADMIN_PASSWORD=admin123 

 

**🐳 Docker Deployment**


Build image: 

docker build -t blog-api . 
Run container: 
docker run -p 8080:8080 blog-api 

 

**🌍 Deployment Flow (Render)**


Push code to GitHub  
Connect repository to Render  
Set environment variables  
Select Docker deployment  
Deploy service  

 

**🧪 Testing**


Use Postman or Swagger: 

/auth/register  
/auth/login  
JWT token required for protected routes  

 

**📊 Monitoring**


Spring Boot Actuator enabled  
Health check: /actuator/health  
Metrics: /actuator/metrics  

 

**🔥 Key Engineering Features**


Clean layered architecture  
Authorization service abstraction  
Role-based access control  
Ownership-based security  
Environment separation (dev/prod)  
Dockerized deployment  
Production-ready configuration  

 

**🚀 Future Improvements**


Redis caching  
Event-driven architecture (Kafka/RabbitMQ)  
File upload system (images for posts)  
CI/CD pipeline (GitHub Actions)  
API rate limiting  
Unit & integration test coverage  

 

**👨‍💻 Author**


Built by: ISRAEL ENDA ILANIE (ME)
Project type: Backend Engineering Portfolio (Spring Boot) 

 
