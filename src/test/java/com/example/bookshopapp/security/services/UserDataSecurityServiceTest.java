package com.example.bookshopapp.security.services;

import com.example.bookshopapp.security.data.UserDataSecurity;
import com.example.bookshopapp.security.repository.UserDataSecurityRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDataSecurityServiceTest {
    private final UserDataSecurityService userDataSecurityService;

    @MockBean
    private UserDataSecurityRepository userDataSecurityRepository;

    @Autowired
    public UserDataSecurityServiceTest(UserDataSecurityService userDataSecurityService, UserDataSecurityRepository userDataSecurityRepository) {
        this.userDataSecurityService = userDataSecurityService;
        this.userDataSecurityRepository = userDataSecurityRepository;
    }

    @Test
    void findUserDataSecurityByEmailIsFound() {
        when(userDataSecurityRepository.findUserDataSecurityByEmail(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    UserDataSecurity userDataSecurity = new UserDataSecurity();
                    userDataSecurity.setEmail(invocation.getArgument(0));
                    return userDataSecurity;
                });

        UserDataSecurity userDataSecurity = generateRundomUserDataSecurity();

        assertNotNull(userDataSecurityService.findUserDataSecurityByEmail(userDataSecurity.getEmail()));
        assertEquals(userDataSecurity.getEmail(),
                userDataSecurityService.findUserDataSecurityByEmail(userDataSecurity.getEmail()).getEmail());
    }

    @Test
    void findUserDataSecurityByEmailIsNotFound() {
        when(userDataSecurityRepository.findUserDataSecurityByEmail(Mockito.anyString()))
                .thenReturn(null);

        UserDataSecurity userDataSecurity = generateRundomUserDataSecurity();

        assertNull(userDataSecurityService.findUserDataSecurityByEmail(userDataSecurity.getEmail()));
    }

    @Test
    void findUserDataSecurityByPhoneIsFound() {
        when(userDataSecurityRepository.findUserDataSecurityByPhone(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    UserDataSecurity userDataSecurity = new UserDataSecurity();
                    userDataSecurity.setPhone(invocation.getArgument(0));
                    return userDataSecurity;
                });

        UserDataSecurity userDataSecurity = generateRundomUserDataSecurity();

        assertNotNull(userDataSecurityService.findUserDataSecurityByPhone(userDataSecurity.getPhone()));
        assertEquals(userDataSecurity.getPhone(),
                userDataSecurityService.findUserDataSecurityByPhone(userDataSecurity.getPhone()).getPhone());
    }

    @Test
    void findUserDataSecurityByPhoneIsNotFound() {
        when(userDataSecurityRepository.findUserDataSecurityByPhone(Mockito.anyString()))
                .thenReturn(null);

        UserDataSecurity userDataSecurity = generateRundomUserDataSecurity();

        assertNull(userDataSecurityService.findUserDataSecurityByPhone(userDataSecurity.getPhone()));
    }

    @Test
    void saveIsSuccessful() {
        when(userDataSecurityRepository.save(Mockito.any(UserDataSecurity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserDataSecurity userDataSecurity = generateRundomUserDataSecurity();

        assertNotNull(userDataSecurityService.save(userDataSecurity));
        assertEquals(userDataSecurity, userDataSecurityService.save(userDataSecurity));
    }

    @Test
    void saveIsUnsuccessful() {
        when(userDataSecurityRepository.save(Mockito.any(UserDataSecurity.class)))
                .thenReturn(null);

        UserDataSecurity userDataSecurity = generateRundomUserDataSecurity();

        assertNull(userDataSecurityService.save(userDataSecurity));
    }

    @Test
    void findUserDataSecurityIsFound() {
        when(userDataSecurityRepository.findById(Mockito.anyInt()))
                .thenAnswer(invocation -> {
                    UserDataSecurity userDataSecurity = new UserDataSecurity();
                    userDataSecurity.setId(invocation.getArgument(0));
                    return Optional.of(userDataSecurity);
                });

        UserDataSecurity userDataSecurity = generateRundomUserDataSecurity();

        assertNotNull(userDataSecurityService.findUserDataSecurity(userDataSecurity.getId()));
        assertEquals(userDataSecurity.getId(), userDataSecurityService.findUserDataSecurity(userDataSecurity.getId()).getId());
    }

    @Test
    void findUserDataSecurityIsNotFound() {
        when(userDataSecurityRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        UserDataSecurity userDataSecurity = generateRundomUserDataSecurity();

        assertThrows(NoSuchElementException.class, () ->
                userDataSecurityService.findUserDataSecurity(userDataSecurity.getId())
        );
    }

    private UserDataSecurity generateRundomUserDataSecurity() {
        int id = Integer.parseInt(generateNumber(2));
        String name = RandomString.make();
        String email = name + "@gmail.com";
        String phone = generateNumber(10);
        String pass = RandomString.make(10);

        UserDataSecurity userDataSecurity = new UserDataSecurity();
        userDataSecurity.setId(id);
        userDataSecurity.setName(name);
        userDataSecurity.setEmail(email);
        userDataSecurity.setPhone(phone);
        userDataSecurity.setPassword(pass);

        return userDataSecurity;
    }

    private String generateNumber(Integer length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(10));
        }

        return builder.toString();
    }
}