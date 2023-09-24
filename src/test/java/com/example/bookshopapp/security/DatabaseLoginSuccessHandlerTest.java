package com.example.bookshopapp.security;

import com.example.bookshopapp.security.data.UserDataSecurity;
import com.example.bookshopapp.security.data.UserDataSecurityDetails;
import com.example.bookshopapp.security.services.UserDataSecurityDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest
class DatabaseLoginSuccessHandlerTest {
    private final String email = "test@test.test";
    private final String name = "test";
    private final String phone = "+7-(777)-777-77-77";
    private final String pass = "111111";

    @MockBean
    private UserDataSecurityDetailService userService;

    private final DatabaseLoginSuccessHandler successHandler;

    @Autowired
    public DatabaseLoginSuccessHandlerTest(DatabaseLoginSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Test
    void onAuthenticationSuccess_ShouldUpdateAuthenticationType() throws ServletException, IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        Authentication authentication = mock(Authentication.class);
        UserDataSecurity userDataSecurity = new UserDataSecurity(name,email, phone, pass);
        UserDetails userDetails = new UserDataSecurityDetails(userDataSecurity);

        when(authentication.getPrincipal()).thenReturn(userDetails);

        successHandler.onAuthenticationSuccess(request, response, authentication);

        verify(userService).updateAuthenticationType(email, "database");
    }
}
