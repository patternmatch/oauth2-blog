# Spring Boot 2 and OAuth2 integration: complete guide
In this project we present how to improve your SpringBoot app's security by using OAuth2 authentication.
The full content you can find in our blog post [HERE](https://pattern-match.com/blog/2018/10/17/springboot2-with-oauth2-integration/).

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

## Available SpringBoot profiles
There are two:
* default - for local development, uses H2 in memory database, enabled by default
* prod - uses AWS RDS and PostgreSQL database, also credentials are stored in AWS Secrets Manager

To use this profile you need to set the following env vars:
* AWS_DEFAULT_REGION
* AWS_REGION
* AWS_ACCESS_KEY_ID
* AWS_SECRET_ACCESS_KEY

## Testing the application
You need any HTTP client. The following uses [HTTPie](https://github.com/jakubroztocil/httpie).

First, request an authentication token.
There are two users available.

Admin user:
```
http -a my-client:my-secret --form POST http://localhost:8080/oauth/token username='admin1@pm.com' password='admin123' grant_type='password'
```

Regular user:
```
http -a my-client:my-secret --form POST http://localhost:8080/oauth/token username='user1@pm.com' password='user123' grant_type='password'
```

You should receive an authentication token in a response. The example below.

```
{
    "access_token": "1bbea46b-93fe-4efa-b25a-eb6d5fac60c0",
    "refresh_token": "9d0e195c-3077-458a-8906-75f2596a48db",
    "scope": "read write trust",
    "token_type": "bearer"
}
```

Now, use the access_token to access the REST API.

```
http http://localhost:8080/api/hello name=='Seb' access_token=='1bbea46b-93fe-4efa-b25a-eb6d5fac60c0'
```
You should see `HTTP/1.1 200` in a response.

You can also list all of the active authentication tokens. It's available only for the admin user.

```
http http://localhost:8080/admin/token/list access_token=='1bbea46b-93fe-4efa-b25a-eb6d5fac60c0'
```
You should receive a list in a response.

You can also revoke the authentication token using the following endpoint.

```
http DELETE http://localhost:8080/oauth/revoke access_token=='1bbea46b-93fe-4efa-b25a-eb6d5fac60c0'
```

You should see `HTTP/1.1 200` in a response.
This token will be removed and you won't be able to access the application using it.

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

3. **jdbc-token-store-and-liquibase**
  * using jdbc store for tokens
  * using embedded H2 database
  * using liquibase to manage database
  
4. **in-database-with-authentication-provider**
  * using authentication provider instead of user details service
  * injecting user name from the security context

5. **roles-and-admin-panel**
  * storing users in the database
  * configuring authorization
  * adding administration panel
  
6. **authentication-token-revocation**
  * revoking OAuth2 authentication token
  * simplifying administration panel
  * using _UserDetailsService_

7. **rds-and-aws-secrets-manager-sdk**
  * introducing dev and prod profiles
  * dev profile uses H2 in memory database (default)
  * prod profile uses PostgreSQL AWS RDS database
  * database credentials are stored and retrieved from AWS Secrets Manager 
