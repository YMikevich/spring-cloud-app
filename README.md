# Tweets Analyzer

The project searches and analyzes tweets.

## Description

![alt text](https://drive.google.com/file/d/1OVQXDYa_sdjjEjrIrfZ_5KUEMBkNJJJh/view)
//todo add more information

## Build and run guide

To run the application in docker container you should have docker and docker-compose pre-installed on your system.
Also you should create your own .env file in the root directory of the project with Twitter API and RabbitMQ credentials.
1. CD into the root directory of the project(where the docker-compose.yml file is present).
2. Run "docker-compose up" command

.env file template: \
CONSUMER_KEY=your consumer key for Twitter API \
CONSUMER_SECRET=your consumer secret for Twitter API \
ACCESS_TOKEN=your consumer access token for Twitter API \
ACCESS_TOKEN_SECRET=your consumer token secret for Twitter API \
RABBIT_HOST=host name for RabbitMQ \
RABBIT_USERNAME=user name for RabbitMQ \
RABBIT_PASSWORD=user password for RabbitMQ

## Running the tests

//todo add information about tests

## Built With

* [Spring Cloud](https://spring.io/projects/spring-cloud) - The micro-service framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot) - Used to generate RSS Feeds
* [Git](https://git-scm.com/doc) - Used for version controlling

## Authors

* **Yauheni Mikevich** - *Initial work* - [Tweets Analyzer](https://github.com/YMikevich/spring-cloud-app)

See also the list of [contributors](https://github.com/YMikevich/spring-cloud-app/graphs/contributors) who participated in this project.