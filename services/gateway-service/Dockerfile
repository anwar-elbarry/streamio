# Phase 1: Build (Maven + JDK + Dependency Cache ⚡)
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app

# Copy pom.xml first → Cache dependencies (changes rarely)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Phase 2: Runtime (Alpine JRE ~150MB ⚡)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy JAR from builder stage (specific to gateway-service)
COPY --from=builder /app/target/gateway-service-*.jar app.jar

# Gateway runs on port 8080
EXPOSE 8080

# Production JVM tuning
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=25.0 \
              -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
