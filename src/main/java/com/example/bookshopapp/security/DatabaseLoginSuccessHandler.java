package com.example.bookshopapp.security;

import com.example.bookshopapp.security.data.UserDataSecurityDetails;
import com.example.bookshopapp.security.services.UserDataSecurityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    final private UserDataSecurityDetailService userService;

    @Autowired
    public DatabaseLoginSuccessHandler(UserDataSecurityDetailService userDataSecurityDetailService) {
        this.userService = userDataSecurityDetailService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        UserDataSecurityDetails userDataSecurityDetailService = (UserDataSecurityDetails) authentication.getPrincipal();
        userService.updateAuthenticationType(userDataSecurityDetailService.getEmail(), "database");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}