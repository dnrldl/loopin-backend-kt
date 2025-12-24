ARG JAR_FILE=build/libs/*-SNAPSHOT.jar

FROM gradle:8.6-jdk21 AS builder
WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN ./gradlew dependencies --no-daemon || true

COPY src ./src

RUN ./gradlew bootJar --no-daemon


FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
