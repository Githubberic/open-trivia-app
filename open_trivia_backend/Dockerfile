#
# Build stage
#
FROM gradle:7.6.1-jdk17 AS build

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Build the project and skip tests
RUN gradle clean build -x test

#
# Package stage
#
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/open_trivia-0.0.1-SNAPSHOT.jar /app/open_trivia.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "/app/open_trivia.jar"]
