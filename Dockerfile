FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S fiap && adduser -S spaceuser -G fiap

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

USER spaceuser

EXPOSE 8080

ENV SERVER_PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]