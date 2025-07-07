FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the already-built jar from your machine
COPY target/file-encryptor.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
