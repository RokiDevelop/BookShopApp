package com.example.bookshopapp.security.services;

import com.example.bookshopapp.security.data.AuthenticationType;
import com.example.bookshopapp.security.data.UserDataSecurity;
import com.example.bookshopapp.security.repository.UserDataSecurityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDataSecurityDetailServiceTest {
    private final String email = "test@test.test";
    private final String name = "test";
    private final String phone = "+7-(777)-777-77-77";
    private UserDataSecurityDetailService userDetailsService;

    @MockBean
    private UserDataSecurityRepository userRepository;

    @Autowired
    public UserDataSecurityDetailServiceTest(UserDataSecurityDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Test
    public void testLoadUserByUsernameWithEmail() {
        UserDataSecurity userDataSecurity = new UserDataSecurity();
        userDataSecurity.setEmail(email);
        userDataSecurity.setName(name);

        when(userRepository.findUserDataSecurityByEmail(email)).thenReturn(userDataSecurity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(name, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsernameWithPhone() {
        UserDataSecurity userDataSecurity = new UserDataSecurity();
        userDataSecurity.setPhone(phone);
        userDataSecurity.setName(name);

        when(userRepository.findUserDataSecurityByPhone(phone)).thenReturn(userDataSecurity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

        assertNotNull(userDetails);
        assertEquals(name, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findUserDataSecurityByEmail(email)).thenReturn(null);
        when(userRepository.findUserDataSecurityByPhone(phone)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(email);
        });
    }

    @Test
    public void testUpdateAuthenticationType() {
        UserDataSecurity userDataSecurity = new UserDataSecurity();
        userDataSecurity.setEmail("test@example.com");

        when(userRepository.updateAuthenticationType("test@example.com", AuthenticationType.FACEBOOK)).thenReturn(Optional.of(userDataSecurity));

        boolean result = userDetailsService.updateAuthenticationType("test@example.com", "facebook");

        assertTrue(result);
    }

    @Test
    public void testUpdateAuthenticationTypeNotFound() {
        when(userRepository.updateAuthenticationType("nonexistent@example.com", AuthenticationType.GOOGLE)).thenReturn(Optional.empty());

        boolean result = userDetailsService.updateAuthenticationType("nonexistent@example.com", "google");

        assertFalse(result);
    }
}