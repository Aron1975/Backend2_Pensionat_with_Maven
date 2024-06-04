package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetToken;
import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.Role;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.*;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetTokenService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final KundRepo kundRepo;
    private final BokningRepo bokningsRepo;
    private final RoleRepo roleRepo;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepo passwordResetTokenRepo;



    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .map(this::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordToken);
    }
    @Override
    public UserDto userToUserDto(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(roles)
                .password(user.getPassword())
                .build();
    }

    @Override
    public User userDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getRoles() != null) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(roleRepo::findByName)
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return user;
    }

    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
        return passwordResetTokenService.validatePasswordResetToken(passwordResetToken);
    }

    @Override
    public User findUserByPasswordToken(String passwordResetToken) {
        return passwordResetTokenService.findUserByPasswordToken(passwordResetToken).get();
    }

    @Override
    public void spara(UserDto userDto) {
        User user = userDtoToUser(userDto);
        userRepo.save(user);
    }

    @Override
    public void resetUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

    }

    public void deleteUserById(UUID id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDto getUser(UUID id) {
        return userRepo.findById(id)
                .map(this::userToUserDto)
                .orElse(null);
    }

    @Override
    public boolean checkIfUserExists(User user, List<User> userList) {

        return false;
    }

    @Override
    public boolean checkIfTokenExist(User user){

        System.out.println("I check if token exists. User: " + user.getUsername());
//        if(passwordResetTokenRepo.findAll().stream().map(u -> u.getUser().getId() == user.getId()).findAny().isPresent()){
//            System.out.println("I check if token exists. Token found ");
//            return true;
//        }
        if(passwordResetTokenRepo.findPasswordResetTokenByUser(user) == null) {
            return false;
        }
        return true;
        //return Boolean.TRUE.equals(passwordResetTokenRepo.findAll().stream().map(u -> u.getUser().getId() == user.getId()).findAny().orElse(null));

    }

}