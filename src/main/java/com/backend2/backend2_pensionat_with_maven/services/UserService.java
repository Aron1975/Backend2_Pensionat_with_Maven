package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public List<UserDto> getAllUsers();

    Optional<User> findByUsername(String username);

    public UserDto userToUserDto(User u);

    void createPasswordResetTokenForUser(User user, String passwordToken);

    User userDtoToUser(UserDto dto);

    String validatePasswordResetToken(String passwordResetToken);
    public void deleteUserById(UUID id);

    User findUserByPasswordToken(String passwordResetToken);

    void spara(UserDto userDto);

    void resetUserPassword(User user, String newPassword);
    public UserDto getUser(UUID id);

    public boolean checkIfUserExists(User user, List<User> userList);

    public boolean checkIfTokenExist(User user);
}