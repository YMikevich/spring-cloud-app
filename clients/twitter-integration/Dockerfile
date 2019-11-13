FROM hirokimatsumoto/alpine-openjdk-11

RUN apk update && apk add bash

VOLUME /tmp
ADD ./target/twitter-integration-0.0.1-SNAPSHOT.jar twitter.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/twitter.jar"]