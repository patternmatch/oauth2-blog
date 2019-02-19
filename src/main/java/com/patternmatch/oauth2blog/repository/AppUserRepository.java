package com.patternmatch.oauth2blog.repository;

import com.patternmatch.oauth2blog.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

}
