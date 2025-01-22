# Use Maven with OpenJDK 17 to build the project
FROM maven:3-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy all files from the current directory into the container
COPY . .

# Build the application and skip tests
RUN mvn clean package -DskipTests

# Use OpenJDK 17 as the base image for the runtime environment
FROM openjdk:17.0.1-jdk-slim

# Set the working directory in the runtime container
WORKDIR /app

# Copy the built JAR file from the build stage into the runtime container
COPY --from=build /app/target/Loan_calculator-0.0.1-SNAPSHOT.jar loan-calculator.jar


# Expose port 9000 for the application
EXPOSE 9500

# Command to run the application
ENTRYPOINT ["java", "-jar", "loan-calculator.jar"]
