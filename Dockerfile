FROM openjdk:17
COPY infinity-api/build/libs/infinity-api-0.0.1-SNAPSHOT.jar infinity.jar
ENTRYPOINT ["java","-jar", "/infinity.jar"]