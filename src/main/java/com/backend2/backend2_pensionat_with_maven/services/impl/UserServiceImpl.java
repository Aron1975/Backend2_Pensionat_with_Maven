package com.backend2.backend2_pensionat_with_maven.services.impl;


import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.dtos.KundDto;
import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.services.KundService;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KundRepo kundRepo;
    private final BokningRepo bokningsRepo;
    private final UserRepo userRepo;


    @Override
    public List<UserDto> getAllUser() {
        return userRepo.findAll().stream().map(u -> userToUserDto(u)).toList();
    }

    @Override
    public UserDto userToUserDto(User u) {
        return UserDto.builder().id(u.getId()).username(u.getUsername()).password(u.getPassword()).build();
    }

    @Override
    public User userDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    public String spara(UserDto u){
        User user = userDtoToUser(u);
        userRepo.save(user);
    }

    @Override
    public void deleteUserById(int id){
        User userToDelete = userRepo.findById(id).orElse(null);
        if (userToDelete != null){
            userRepo.deleteById(id);
        }
    }

    public UserDto getUser(int id){
        User user = userRepo.findById(id).orElse(null);
        if (user != null){
            return userToUserDto(user);
        }
        return null;
    }


}
