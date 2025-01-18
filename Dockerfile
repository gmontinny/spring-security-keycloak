FROM alpine:3.17.10
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]