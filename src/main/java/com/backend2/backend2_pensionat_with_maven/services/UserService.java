package com.backend2.backend2_pensionat_with_maven.services;



import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.User;

import java.util.List;

public interface UserService {


    
    public List<UserDto> getAllUser();

    public UserDto userToUserDto(User u);

    public User UserDtoToUser(UserDto u);

    public void deleteUserById(int id);

    public String spara(UserDto u);

    public UserDto getUser(int id);




}
