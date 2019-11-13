FROM hirokimatsumoto/alpine-openjdk-11

RUN apk update && apk add bash

VOLUME /tmp
ADD ./target/config-server-0.0.1-SNAPSHOT.jar config-server.jar
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/config-server.jar"]