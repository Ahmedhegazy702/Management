package com.management.management.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    boolean login (String email,String password);
    UserDetails loadUserByUsername(String email);
}
