package com.example.bookshopapp.security.oauth;

import com.example.bookshopapp.security.services.UserDataSecurityDetailService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserDataSecurityDetailService userDataSecurityDetailService;

    @Autowired
    public OAuthLoginSuccessHandler(UserDataSecurityDetailService userDataSecurityDetailService) {
        this.userDataSecurityDetailService = userDataSecurityDetailService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String oauth2ClientName = oauth2User.getOauth2ClientName();
        String userEmail = oauth2User.getEmail();
        userDataSecurityDetailService.updateAuthenticationType(userEmail, oauth2ClientName);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
