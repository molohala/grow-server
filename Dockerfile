FROM openjdk:17
COPY grow-api/build/libs/grow-api-0.0.1-SNAPSHOT.jar grow.jar
ENTRYPOINT ["java","-jar", "/grow.jar"]