

# Use a lightweight base image for Java
FROM openjdk:17-jdk-slim



# Copy the compiled JAR from the build stage into the container
COPY --from=build /app/target/doctorappointment.jar app.jar
# Expose the port your Spring Boot application is running on (change the port accordingly)
#EXPOSE 8080

# Run the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]
