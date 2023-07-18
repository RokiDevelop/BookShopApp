package com.example.bookshopapp.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private final JwtTokenEntityService jwtTokenEntityService;

    @Autowired
    public CustomLogoutHandler(JwtTokenEntityService jwtTokenEntityService) {
        this.jwtTokenEntityService = jwtTokenEntityService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        String token = findToken(cookies);
        blockedToken(token);
        invalidateSession(request.getSession());
        clearCookies(request, response);

        response.sendRedirect("/signin");
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        Cookie deleteServletCookie = new Cookie("token", null);
        deleteServletCookie.setMaxAge(0);
        response.addCookie(deleteServletCookie);
    }

    private void invalidateSession(HttpSession session) {
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
    }

    private String findToken(Cookie[] cookies) {
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        return token;
    }

    private void blockedToken(String token) {

        if (token != null) {
            JwtTokenEntity tokenEntity = new JwtTokenEntity();
            tokenEntity.setBlocked(true);
            tokenEntity.setJwtString(token);

            jwtTokenEntityService.blockToken(tokenEntity);
        }
    }
}
