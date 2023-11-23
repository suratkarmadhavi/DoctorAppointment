
# Use a lightweight base image for Maven
FROM maven:3.8.3-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the application using Maven
RUN mvn clean install -U

# Use a lightweight base image for Java
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app
RUN ls

RUN pwd

# Copy the compiled JAR from the build stage into the container
COPY --from=build /app/target/doctorappointment.jar app.jar
# Expose the port your Spring Boot application is running on (change the port accordingly)
#EXPOSE 8080

# Run the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]
