package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.models.Role;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.RoleRepo;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDataSeeder {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    public void Seed(){
        if (roleRepo.findByName("Admin") == null) {
            addRole("Admin");
        }
        if (roleRepo.findByName("Receptionist") == null) {
            addRole("Receptionist");
        }
        if (roleRepo.findByName("Customer") == null) {
            addRole("Customer");
        }
        if(userRepo.getUserByUserName("asdf@123.se").isEmpty()){
            addUser("asdf@123.se","Admin");
        }
        if(userRepo.getUserByUserName("hejhej@123.se").isEmpty()) {
            addUser("hejhej@123.se", "Receptionist");
        }
        if(userRepo.getUserByUserName("qwerty@123.se").isEmpty()){
            addUser("qwerty@123.se","Customer");
        }
    }

    private void addUser(String mail, String group) {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName(group));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("Hejsan123#");
        User user = User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
        userRepo.save(user);
    }

    private void addRole(String name) {
        Role role = new Role();
        roleRepo.save(Role.builder().name(name).build());
    }

}
