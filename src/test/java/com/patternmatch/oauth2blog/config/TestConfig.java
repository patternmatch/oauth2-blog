package com.patternmatch.oauth2blog.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser1 = new org.springframework.security.core.userdetails.User(
                "user1@test.com",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        User basicUser2 = new org.springframework.security.core.userdetails.User(
                "user2@test.com",
                "password",
                Collections.emptyList());

        return new InMemoryUserDetailsManager(basicUser1, basicUser2);
    }
}