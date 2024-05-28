package com.backend2.backend2_pensionat_with_maven;


import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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

    User userTempNotExist = new User();

    User userTempExist = new User();


    List<User> userList;

    @InjectMocks
    UserServiceImpl userServiceImpl;  //  = new UserServiceImpl(kundRepo,bokningRepo,userRepo);

    @BeforeEach
    void setup() {

        userTempNotExist.setPassword("12345");
        userTempNotExist.setUsername("Hejsan");

        userTempExist.setPassword("111222");
        userTempExist.setUsername("FinnsRedan");

        User listFillingTemp = new User();
        listFillingTemp.setUsername("FinnsRedan");
        listFillingTemp.setPassword("111222");

        userList = new ArrayList<>();
        userList.add(listFillingTemp);

      //  when(userTempNotExist.getUsername()).thenReturn(userTempNotExist.password);
       // when(userTempNotExist.getPassword()).thenReturn(userTempNotExist.password);

    }

    /*  method to test
    public boolean checkIfUserExists(User user, List<User> userList) {
        String uName = user.getUsername();
        String pWord = user.getPassword();

        boolean userExists = userList.stream().anyMatch(u -> u.getUsername().equals(uName) &&
                u.getPassword().equals(pWord));

        return userExists;
    }

     */
/*
    @Test
    void checkIfUserExistsTest(){  //testar att "userTempNotExist" ger false, och "UserTempExist" ger true, alltså att första inte finns

        boolean shouldExist;
        boolean shouldNotExist;

        shouldExist = userServiceImpl.checkIfUserExists(userTempExist, userList);
        shouldNotExist = userServiceImpl.checkIfUserExists(userTempNotExist, userList);

        assertEquals(true, shouldExist);
        assertEquals(false, shouldNotExist);

       assertNotEquals(false, shouldExist);
       assertNotEquals(true, shouldNotExist);

*/
    }







