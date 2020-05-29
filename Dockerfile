FROM openjdk:8-jdk-alpine
LABEL maintainer="thongo5430@gmail.com"
VOLUME /tmp
EXPOSE 8080
ADD target/trillion-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]