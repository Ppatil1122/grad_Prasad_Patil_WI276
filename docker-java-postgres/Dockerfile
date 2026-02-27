# Use Maven image to compile
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Runtime container
FROM eclipse-temurin:17

WORKDIR /app
COPY --from=build /app/target/docker-demo-1.0.jar app.jar

CMD ["java", "-cp", "app.jar:app.jar", "App"]