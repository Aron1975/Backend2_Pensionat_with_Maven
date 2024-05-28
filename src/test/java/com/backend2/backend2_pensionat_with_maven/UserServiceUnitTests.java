package com.backend2.backend2_pensionat_with_maven;


import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.repos.RoleRepo;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {


    @Mock
    KundRepo kundRepo;

    @Mock
    UserRepo userRepo;

    @Mock
    BokningRepo bokningRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepo roleRepo;


    @Mock
    User userTempNotExist = new User();



    @Mock
    User userTempExist = new User();


    List<User> userList;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userTempNotExist.setPassword("12345");
        userTempNotExist.setUsername("Hejsan");

        userTempExist.setPassword("111222");
        userTempExist.setUsername("FinnsRedan");

      //  User listFillingTemp = new User();
      //  listFillingTemp.setUsername("FinnsRedan");
       // listFillingTemp.setPassword("111222");

        userList = new ArrayList<>();
        userList.add(userTempExist);


     //   when(userTempNotExist.getUsername()).thenReturn(userTempNotExist.getUsername());
     //   when(userTempNotExist.getPassword()).thenReturn(userTempNotExist.getPassword());

    }

    /*  method to test
     @Override
    public boolean checkIfUserExists(User user, List<User> userList) {
        return userList.stream().anyMatch(existingUser ->
                existingUser.getUsername().equals(user.getUsername()) &&
                        passwordEncoder.matches(user.getPassword(), existingUser.getPassword()));
    }
    }

     */
    @Test
    void checkIfUserExistsTest() {  //testar att "userTempNotExist" ger false, och "UserTempExist" ger true, alltså att första inte finns

        boolean shouldExist;

        when(passwordEncoder.matches("111222", "111222")).thenReturn(true);
        shouldExist = userServiceImpl.checkIfUserExists(userTempExist, userList);

        assertEquals(true, shouldExist);


    }
/*

    @Test
    void checkIfUserNotExistTest() {

        boolean shouldNotExist;

        when(passwordEncoder.matches("111222", "111222")).thenReturn(false);
        shouldNotExist = userServiceImpl.checkIfUserExists(userTempNotExist, userList);
        assertEquals(false, shouldNotExist);

        assertNotEquals(true, shouldNotExist);
    }

 */
}







