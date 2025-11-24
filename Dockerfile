FROM eclipse-temurin:17-jre

WORKDIR /app
COPY build/app-fat.jar app.jar

CMD ["java", "-jar", "app.jar"]
