package com.backend2.backend2_pensionat_with_maven.services;



import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.User;

import java.util.List;

public interface UserService {


    
    public List<UserDto> getAllUsers();

    public UserDto userToUserDto(User u);

    public User userDtoToUser(UserDto u);

    public void deleteUserById(int id);

    public void spara(UserDto u);

    public UserDto getUser(int id);

    public boolean checkIfUserExists(User user, List<User> userList);

}
