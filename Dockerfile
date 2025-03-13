FROM maven:3.8.5-openjdk-17-slim AS MAVEN_BUILD
COPY . .
RUN mvn clean package

FROM openjdk:17-slim-buster
COPY --from=MAVEN_BUILD /target/*.jar /app.jar

CMD ["java", "-XX:+UseG1GC", "-jar", "/app.jar"]
EXPOSE 8082