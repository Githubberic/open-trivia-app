FROM gradle:7.6.1-jdk17 AS build

WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=build /app/build/libs/open_trivia-0.0.1-SNAPSHOT.jar /app/open_trivia.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/open_trivia.jar"]
