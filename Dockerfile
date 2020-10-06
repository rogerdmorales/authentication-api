FROM docker.io/library/openjdk:8u212-slim AS build

COPY pom.xml /app/pom.xml
COPY .mvn /app/.mvn
COPY mvnw /app/mvnw

WORKDIR /app

COPY . /app/
RUN ./mvnw package -DskipTests

FROM docker.io/library/openjdk:8u212-jre-slim

COPY --from=build /app/target/authentication-api-*.jar /authentication-api.jar
ENTRYPOINT ["/usr/local/openjdk-8/bin/java", "-jar", "/authentication-api.jar"]
EXPOSE 8080
USER www-data
