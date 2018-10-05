package com.patternmatch.oauth2blog.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mockUser(username);
    }

    private UserDetails mockUser(String username) {
        String userName = "test@test.com";
        String userPass = "tester";

        if (!userName.equals(username)) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        // this is another way of dealing with password encoding
        // password will be stored in bcrypt in this example
        // you can also use a prefix, @see com.patternmatch.oauth2blog.config.AuthorizationServerConfig#CLIENT_SECRET
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(username)
                .password(userPass)
                .authorities(getAuthority())
                .build();

        return user;
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.emptyList();
    }
}