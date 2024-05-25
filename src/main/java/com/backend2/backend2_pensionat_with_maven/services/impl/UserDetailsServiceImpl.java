package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.security.ConcreteUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepo.getUserByUserName(username);

        if (foundUser.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }

        User user = foundUser.get();
        System.out.println("Loaded user: " + user.getUsername() + ", password: " + user.getPassword());

        return new ConcreteUserDetails(user);
    }
}