FROM hirokimatsumoto/alpine-openjdk-11

RUN apk update && apk add bash

VOLUME /tmp
ADD ./target/spring-data-jpa-user-service-0.0.1-SNAPSHOT.jar user-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/user-service.jar"]