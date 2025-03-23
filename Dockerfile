FROM eclipse-temurin:23 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven

RUN mvn clean install -DskipTests

FROM eclipse-temurin:23
WORKDIR /app

COPY --from=builder /app/target/internal-0.0.1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
