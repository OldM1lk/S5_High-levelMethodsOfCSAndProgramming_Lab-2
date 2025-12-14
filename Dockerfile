FROM eclipse-temurin:17-jre

WORKDIR /app
COPY lab2-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
