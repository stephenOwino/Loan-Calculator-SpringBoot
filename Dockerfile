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

# Install NGINX
RUN apt-get update && \
    apt-get install -y nginx && \
    apt-get clean

# Copy the built JAR file from the build stage into the runtime container
COPY --from=build /app/target/Loan_calculator-0.0.1-SNAPSHOT.jar loan-calculator.jar

# Copy your nginx.conf configuration file into the container
COPY nginx.conf /etc/nginx/nginx.conf

# Expose ports for both the application and NGINX (9500 for the app and 80 for NGINX)
EXPOSE 9500 80

# Start both the Spring Boot application and NGINX service
CMD service nginx start && java -jar loan-calculator.jar
