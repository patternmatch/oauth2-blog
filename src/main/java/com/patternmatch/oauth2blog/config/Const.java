package com.patternmatch.oauth2blog.config;

public final class Const {

    public static final String CLIENT_ID = "my-client";
    // encoding method prefix is required for DelegatingPasswordEncoder which is default since Spring Security 5.0.0.RC1
    // you can use one of bcrypt/noop/pbkdf2/scrypt/sha256
    // you can change default behaviour by providing a bean with the encoder you want
    // more: https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-encoding
    public static final String CLIENT_SECRET = "{noop}my-secret";
}
