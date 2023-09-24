package com.example.bookshopapp.security;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.book.links.Book2UserEntity;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.security.data.*;
import com.example.bookshopapp.security.services.User2UserDataSecurityService;
import com.example.bookshopapp.security.services.UserDataSecurityRegisterService;
import com.example.bookshopapp.security.services.UserDataSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthUserController {

    private final UserDataSecurityRegisterService registrationService;
    private final UserDataSecurityService userDataSecurityService;
    private final User2UserDataSecurityService user2UserDataSecurityService;

    @Autowired
    public AuthUserController(UserDataSecurityRegisterService registrationService,
                              UserDataSecurityService userDataSecurityService,
                              User2UserDataSecurityService user2UserDataSecurityService) {
        this.registrationService = registrationService;
        this.userDataSecurityService = userDataSecurityService;
        this.user2UserDataSecurityService = user2UserDataSecurityService;

    }

    @GetMapping("/signin")
    public String handleSignIn() {
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }


    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model) {
        registrationService.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                   HttpServletResponse httpServletResponse) {
        ContactConfirmationResponse loginResponse = registrationService.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        return loginResponse;

    }

    @GetMapping("/my")
    public String handleMyPage(Model model, Authentication authentication) {
        UserDataSecurityDetails userDetails = (UserDataSecurityDetails) authentication.getPrincipal();
        Integer userId = userDetails.getUserDataSecurity().getId();
        UserDataSecurity userDataSecurity = userDataSecurityService.findUserDataSecurity(userId);
        UserEntity user = null;
        try {
            user = user2UserDataSecurityService.getUserByUserDataSecurityId(userDataSecurity);
        } catch (Exception e) {
            e.getMessage();
        }
        List<Book2UserEntity> book2UserEntityList = user.getBook2UserEntities();
        List<Book> bookList = new ArrayList<>();
        book2UserEntityList.forEach(book2UserEntity -> {
            bookList.add(book2UserEntity.getBook());
        });
        model.addAttribute("bookList", bookList);
        return "my";
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        model.addAttribute("curUser", registrationService.getCurrentUser());
        return "profile";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }

        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        return "redirect:/";
    }
}
