# Use Maven with OpenJDK 17 to build the project
FROM maven:3-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only necessary files to optimize the build
COPY pom.xml .
COPY src ./src

# Build the application and skip tests
RUN mvn clean package -DskipTests

# Use OpenJDK 17 as the base image for the runtime environment
FROM openjdk:17.0.1-jdk-slim AS runtime

# Set the working directory in the runtime container
WORKDIR /app

# Copy the built JAR file from the build stage into the runtime container
COPY --from=build /app/target/Loan_calculator-0.0.1-SNAPSHOT.jar loan-calculator.jar

# Expose the port for the Spring Boot app
EXPOSE 9500

# Start the Spring Boot application
CMD ["java", "-jar", "loan-calculator.jar"]
