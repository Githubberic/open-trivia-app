# Use official Java runtime
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Gradle build output into container
COPY build/libs/*.jar app.jar

# Expose the port Render will use
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]