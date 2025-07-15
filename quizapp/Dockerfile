FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/quizapp-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


