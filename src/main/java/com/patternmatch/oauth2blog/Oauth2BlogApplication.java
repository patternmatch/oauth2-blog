package com.patternmatch.oauth2blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// this is to disable default login page from spring security
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Oauth2BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2BlogApplication.class, args);
    }
}
