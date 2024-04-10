FROM openjdk:17
COPY /infinity-server/infinity-api/build/libs/infinity-api-0.0.1-SNAPSHOT-plain.jar infinity.jar
ENTRYPOINT ["java","-jar", "/infinity.jar"]