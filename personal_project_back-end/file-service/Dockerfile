# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copy parent POM and all module codes
COPY . .
# Build the file-service and its dependencies
RUN mvn -pl file-service -am clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/file-service/target/*.jar app.jar
EXPOSE 9994
ENTRYPOINT ["java", "-jar", "app.jar"]
