package com.backend2.backend2_pensionat_with_maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend2.backend2_pensionat_with_maven.models.Role;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.RoleRepo;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;

import java.util.Collections;

@Service
public class UserDataSeeder {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    public void seed() {
        if (roleRepo.findByName("Admin") == null) {
            addRole("Admin");
        }
        if (roleRepo.findByName("Receptionist") == null) {
            addRole("Receptionist");
        }
        if (userRepo.getUserByUserName("admin@pensionat.com").isEmpty()) {
            addUser("admin@pensionat.com", "Admin");
        }
        if (userRepo.getUserByUserName("receptionist@pensionat.com").isEmpty()) {
            addUser("receptionist@pensionat.com", "Receptionist");
        }
    }

    private void addUser(String email, String roleName) {
        Role role = roleRepo.findByName(roleName);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("password123");

        User user = User.builder()
                .username(email)
                .password(hashedPassword)
                .enabled(true)
                .roles(Collections.singleton(role))
                .build();

        userRepo.save(user);
    }

    private void addRole(String name) {
        Role role = new Role();
        role.setName(name);
        roleRepo.save(role);
    }
}
