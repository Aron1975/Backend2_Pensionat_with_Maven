package com.backend2.backend2_pensionat_with_maven.services;


import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    void deleteUserById(UUID id);  // Standardized to use UUID only

    void saveUser(UserDto userDto);  // Renamed from 'spara' for clarity

    UserDto getUserById(UUID id);  // Updated to use UUID

    boolean checkIfUserExists(UUID userId);  // Simplified to check existence by UUID
}


