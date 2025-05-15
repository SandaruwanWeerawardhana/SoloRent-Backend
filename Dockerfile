# Use OpenJDK base image
FROM openjdk:22-jdk-slim

# Add a volume for logs (optional)
VOLUME /tmp

# Copy the jar file
COPY target/SoloRentBackend-1.0-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

