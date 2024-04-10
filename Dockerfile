FROM openjdk:17
COPY infinity-api/build/libs/infinity-api-0.0.1-SNAPSHOT-plain.jar app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]