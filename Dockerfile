FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/infinity-server-0.0.1-SNAPSHOT-plain.jar

COPY ${JAR_FILE} /infinity.jar

ENTRYPOINT ["java","-jar", "/infinity.jar"]