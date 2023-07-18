package com.example.bookshopapp.security.jwt;

import com.example.bookshopapp.security.UserDataSecurityDetailService;

import com.example.bookshopapp.security.UserDataSecurityDetails;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final UserDataSecurityDetailService detailService;
    private final JWTUtil jwtUtil;

    @Autowired
    public JWTRequestFilter(UserDataSecurityDetailService detailService, JWTUtil jwtUtil) {
        this.detailService = detailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        try {
            if (cookies != null) {
                String token = getTokenFromCookies(cookies);
                String username = getUsernameFromToken(token);
                try {
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        authenticateUser(username, token, request);
                    }
                    checkTokenBlock(token);
                } catch (AccessDeniedException ex) {
                    endSession(response);
                }
            }
        } catch (ExpiredJwtException ex) {
            endSession(response);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromCookies(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    private String getUsernameFromToken(String token) {
        if (token != null && !jwtUtil.isTokenExpired(token)) {
            return jwtUtil.extractUsername(token);
        }
        return null;
    }

    private void authenticateUser(String username, String token, HttpServletRequest request) throws AccessDeniedException {
        UserDataSecurityDetails userDetails = (UserDataSecurityDetails) detailService.loadUserByUsername(username);

        if (jwtUtil.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private void checkTokenBlock(String token) throws AccessDeniedException {
        jwtUtil.checkBlock(token);
    }

    private void endSession(HttpServletResponse response) throws IOException {
        response.sendRedirect("/logout");
    }
}
