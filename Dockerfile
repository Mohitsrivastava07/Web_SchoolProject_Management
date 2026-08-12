# Multi-stage Dockerfile for Spring Boot + Java 21 + Maven
# Build stage: Maven + Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven files first to improve Docker layer caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Make Maven wrapper executable (also keeps the project usable if you later switch to ./mvnw)
RUN chmod +x mvnw

# Download dependencies
RUN mvn -B dependency:go-offline

# Copy application source
COPY src ./src

# Build Spring Boot executable JAR
RUN mvn -B clean package -DskipTests

# Runtime stage: Java 21 only
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built Spring Boot JAR
COPY --from=build /app/target/*.jar /app/app.jar

# Render supplies PORT at runtime; default to 8080 for local Docker runs
EXPOSE 8080

# Use Render's PORT when available, otherwise 8080
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=${PORT:-8080}"]
