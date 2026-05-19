# Use official Java 21 runtime (matches your setup)
FROM eclipse-temurin:21

# Set working directory inside container
WORKDIR /app

# Copy the built jar file into the container
COPY target/blog-api-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]