package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public List<UserDto> getAllUsers();

    public UserDto userToUserDto(User u);

    public User userDtoToUser(UserDto u);

    public void deleteUserById(UUID id);

    public void spara(UserDto u);

    public UserDto getUser(UUID id);

    public boolean checkIfUserExists(User user, List<User> userList);
}
