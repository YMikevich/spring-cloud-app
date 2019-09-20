# Twitter-integration module

The service searches for tweets using twitter username.

## Description

Searching is done by using Twitter4j SDK that uses Twitter API under the hood.

## Build and run docker guide

To build an image use command:
docker build -t twitter-integration .

To run the container use command:
docker run --name twitter-integration \\\
-e twitter4j.oauth.consumerKey=your consumer key \\\
-e twitter4j.oauth.consumerSecret=your consumer secret \\\
-e twitter4j.oauth.accessToken=your access token \\\
-e twitter4j.oauth.accessTokenSecret=your access token secret \\\
-e twitter4j.debug=true \\\
-p 8080:8080 \\\
-v $HOME/twitter-integration/logs:clients/twitter-integration/logs twitter-integration

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