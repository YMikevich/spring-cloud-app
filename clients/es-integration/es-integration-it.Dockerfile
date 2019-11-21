FROM hirokimatsumoto/alpine-openjdk-11

RUN apk update && apk add bash

WORKDIR /app
COPY . /app

ENTRYPOINT ["./gradlew", "integrationTest"]