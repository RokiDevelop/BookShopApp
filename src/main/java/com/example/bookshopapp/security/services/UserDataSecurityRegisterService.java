package com.example.bookshopapp.security.services;

import com.example.bookshopapp.data.book.links.User2UserDataSecurity;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.security.data.*;
import com.example.bookshopapp.security.jwt.JWTUtil;
import com.example.bookshopapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserDataSecurityRegisterService {
    private final UserDataSecurityService userDataSecurityService;
    private final UserService userService;
    private final User2UserDataSecurityService user2UserDataSecurityService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDataSecurityDetailService userDataSecurityDetailService;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserDataSecurityRegisterService(UserDataSecurityService userDataSecurityRepository,
                                           UserService userService,
                                           User2UserDataSecurityService user2UserDataSecurityService,
                                           PasswordEncoder passwordEncoder,
                                           AuthenticationManager authenticationManager,
                                           UserDataSecurityDetailService userDataSecurityDetailService,
                                           JWTUtil jwtUtil) {
        this.userDataSecurityService = userDataSecurityRepository;
        this.userService = userService;
        this.user2UserDataSecurityService = user2UserDataSecurityService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDataSecurityDetailService = userDataSecurityDetailService;
        this.jwtUtil = jwtUtil;
    }

    public User2UserDataSecurity registerNewUser(RegistrationForm registrationForm) {
        if (userDataSecurityService.findUserDataSecurityByEmail(registrationForm.getEmail()) == null &&
                userDataSecurityService.findUserDataSecurityByPhone(registrationForm.getPhone()) == null) {
            UserDataSecurity userDataSecurity = saveNewUserDataSecurity(registrationForm);
            UserEntity user = saveNewUserEntity(registrationForm);

            User2UserDataSecurity user2UserDataSecurity = new User2UserDataSecurity(user, userDataSecurity);
            return user2UserDataSecurityService.save(user2UserDataSecurity);
        }

        throw new IllegalArgumentException("New user was not saved");
    }

    private UserDataSecurity saveNewUserDataSecurity(RegistrationForm registrationForm) {
        UserDataSecurity userDataSecurity = new UserDataSecurity(
                registrationForm.getName(), registrationForm.getEmail(),
                registrationForm.getPhone(), passwordEncoder.encode(registrationForm.getPass()));
        userDataSecurityService.save(userDataSecurity);
        return userDataSecurity;
    }

    private UserEntity saveNewUserEntity(RegistrationForm registrationForm) {
        UserEntity user =
                new UserEntity(
                        registrationForm.getName(),
                        LocalDateTime.now(),
                        registrationForm.getName() + LocalDateTime.now().getYear() +
                                LocalDateTime.now().getDayOfYear() + LocalDateTime.now().getNano(),
                        0);
        userService.save(user);
        return user;
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) throws UsernameNotFoundException {
        String contact = payload.getContact();
        String code = payload.getCode().replace(" ", "");
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(contact, code);
        authenticationManager.authenticate(upat);
        UserDataSecurityDetails userDetails =
                (UserDataSecurityDetails) userDataSecurityDetailService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public UserDataSecurity getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();

        if (auth instanceof OAuth2AuthenticationToken) {
            return getUserDataSecurityByOAuth2AuthenticationToken(auth);
        } else {
            return getUserDataSecurityByUserDataSecurityDetails((UserDataSecurityDetails) auth.getPrincipal());
        }
    }

    private UserDataSecurity getUserDataSecurityByOAuth2AuthenticationToken(Authentication auth) {
        UserDataSecurity userDataSecurity = new UserDataSecurity();
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) auth;
        userDataSecurity.setName(authenticationToken.getPrincipal().getAttribute("name"));
        userDataSecurity.setEmail(authenticationToken.getPrincipal().getAttribute("email"));
        userDataSecurity.setPhone(authenticationToken.getPrincipal().getAttribute("phone"));
        userDataSecurity.setPassword(authenticationToken.getPrincipal().getAttribute("pass"));
        return userDataSecurity;
    }

    private UserDataSecurity getUserDataSecurityByUserDataSecurityDetails(UserDataSecurityDetails userDetails) {
        return userDetails.getUserDataSecurity();
    }
}
