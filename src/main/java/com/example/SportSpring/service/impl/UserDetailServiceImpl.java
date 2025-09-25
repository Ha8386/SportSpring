package com.example.SportSpring.service.impl;

import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found: " + username);
        var authority = new SimpleGrantedAuthority("ROLE_" + user.getRoles());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), Collections.singletonList(authority));
    }


    public class CustomUserDetails {

        public UserEntity getUser() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getUser'");
        }
    }
}
