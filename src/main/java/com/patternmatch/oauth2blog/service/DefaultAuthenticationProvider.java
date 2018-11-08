package com.patternmatch.oauth2blog.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

public class DefaultAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if (authentication.getName() == null || authentication.getCredentials() == null) {
            return null;
        }

        final String userEmail = authentication.getName();
        final Object userPassword = authentication.getCredentials();

        if (userEmail == null || userPassword == null) {
            return null;
        }

        if (userEmail.isEmpty() || userPassword.toString().isEmpty()) {
            return null;
        }

        String validUserEmail = "test@test.com";
        String validUserPassword = "tester";

        if (userEmail.equalsIgnoreCase(validUserEmail)
                && userPassword.equals(validUserPassword)) {
            return new UsernamePasswordAuthenticationToken(
                    userEmail, userPassword, getAuthority());
        }

        throw new UsernameNotFoundException("Invalid username or password.");
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.emptyList();
    }
}
