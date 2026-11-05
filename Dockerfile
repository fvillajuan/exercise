FROM openjdk:17-jdk-slim

COPY build/libs/authorization-0.0.1-SNAPSHOT.jar authorization-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "authorization-0.0.1-SNAPSHOT.jar"]