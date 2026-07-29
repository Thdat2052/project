# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copy parent POM and all module codes
COPY . .
# Build the auth-service and its dependencies
RUN mvn -pl auth-service -am clean package -DskipTests -B --fail-at-end -Dmaven.wagon.http.retryHandler.count=3 -Dmaven.wagon.httpconnectionManager.ttlSeconds=120

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/auth-service/target/*.jar app.jar
EXPOSE 9996
ENTRYPOINT ["java", "-jar", "app.jar"]
