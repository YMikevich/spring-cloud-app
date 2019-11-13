FROM hirokimatsumoto/alpine-openjdk-11

RUN apk update && apk add bash

VOLUME /tmp
ADD ./target/service-discovery-0.0.1-SNAPSHOT.jar service-discovery.jar
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service-discovery.jar"]