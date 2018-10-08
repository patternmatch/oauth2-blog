# Spring Boot 2 and OAuth2 integration: complete guide
In this project we present how to improve your SpringBoot app's security by using OAuth2 authentication.
The full content you can find in our blog post HERE (TODO: add a link).

## Building the project
You can use gradle wrapper which is configured in the project.

```
./gradlew clean build
```

## Running the application
You can use your IDE or command-line.

### IDE
Just run _Oauth2BlogApplication_ as Java application.

### Command-line
You can use gradle for it.

```
./gradlew clean bootRun
```

## Testing the application
Just send HTTP GET request. 
Use your favourite client. The following uses [HTTPie](https://github.com/jakubroztocil/httpie).

```
http http://localhost:8080/api/hello name=='Seb'
```

## Available tags
In the blog post you may see that the application is being built incrementally. 
The following describes the tags corresponding to the each step of development.

1. **empty-with-dependencies**
  * no authentication
  * all the project dependencies added
  * test endpoint exposed

2. **in-memory-with-user-details-service**
  * OAuth2 authentication configured
  * using in-memory store for clients and tokens
  * using _DelegatingPasswordEncoder_
  * added three different ways of testing the app with spring tools
