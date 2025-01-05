FROM openjdk:17-jdk-alpine

WORKDIR /demo

COPY build/libs/demo-0.0.1-SNAPSHOT.jar /demo/demo.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
