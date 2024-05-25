package com.backend2.backend2_pensionat_with_maven.services.impl;


import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public List<UserDto> getAllUsers() {
        Iterable<User> users = userRepo.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .map(this::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public User userDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = userDtoToUser(userDto);
        userRepo.save(user);
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepo.findById(id).ifPresent(userRepo::delete);
    }

    @Override
    public UserDto getUserById(UUID id) {
        return userRepo.findById(id)
                .map(this::userToUserDto)
                .orElse(null);
    }

    @Override
    public boolean checkIfUserExists(UUID userId) {
        return userRepo.findById(userId).isPresent();
    }
}
