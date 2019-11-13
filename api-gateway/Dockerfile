FROM hirokimatsumoto/alpine-openjdk-11

RUN apk update && apk add bash

VOLUME /tmp
ADD ./target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8762
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/api-gateway.jar"]