package com.backend2.backend2_pensionat_with_maven.services;


import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetToken;
import com.backend2.backend2_pensionat_with_maven.models.User;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    //User registerNewUserAccount(UserDto accountDto);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    /*VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);*/

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();


    String isValidNewLocationToken(String token);

    void addUserLocation(User user, String ip);

    //List<User> getAllUsers();

    //UserDto userToUserDto(User u);

    /*List<User> getUsers();

    Optional<User> findByUsername(String username);


    void createPasswordResetTokenForUser(User user, String passwordToken);
    void saveUserVerificationToken(User theUser, String verificationToken);

    String validatePasswordResetToken(String passwordResetToken);

    User findUserByPasswordToken(String passwordResetToken);

    void resetUserPassword(User user, String newPassword);*/
}
