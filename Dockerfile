FROM openjdk:17

ARG JAR_FILE=infinity-api/build/libs/infinity-server-0.0.1-SNAPSHOT-plain.jar

COPY ${JAR_FILE} /infinity.jar

ENTRYPOINT ["java","-jar", "/infinity.jar"]