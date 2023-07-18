package com.example.bookshopapp.security;

import com.example.bookshopapp.security.jwt.CustomLogoutHandler;
import com.example.bookshopapp.security.jwt.JWTRequestFilter;
import com.example.bookshopapp.security.oauth.CustomOAuth2UserService;
import com.example.bookshopapp.security.oauth.OAuthLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService oauth2UserService;
    private final OAuthLoginSuccessHandler oauthLoginSuccessHandler;
    private final DatabaseLoginSuccessHandler databaseLoginSuccessHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final UserDataSecurityDetailService userDataSecurityDetailService;
    private final JWTRequestFilter filter;

    @Autowired
    public SecurityConfig(CustomOAuth2UserService oauth2UserService,
                          OAuthLoginSuccessHandler oauthLoginSuccessHandler,
                          DatabaseLoginSuccessHandler databaseLoginSuccessHandler,
                          CustomLogoutHandler customLogoutHandler,
                          UserDataSecurityDetailService userDataSecurityDetailService,
                          JWTRequestFilter filter) {
        this.oauth2UserService = oauth2UserService;
        this.oauthLoginSuccessHandler = oauthLoginSuccessHandler;
        this.databaseLoginSuccessHandler = databaseLoginSuccessHandler;
        this.customLogoutHandler = customLogoutHandler;
        this.userDataSecurityDetailService = userDataSecurityDetailService;
        this.filter = filter;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDataSecurityDetailService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/my", "/profile")
                .authenticated()//.hasRole("USER")
                .antMatchers("/**")
                .permitAll()
                .and().formLogin()
                .loginPage("/signin")
                .successHandler(databaseLoginSuccessHandler)
                .defaultSuccessUrl("/my")
                .failureUrl("/logout")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signin")
                .logoutSuccessHandler(customLogoutHandler)
                .deleteCookies("token")
                .permitAll()
                .and().oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauth2UserService)
                .and().successHandler(oauthLoginSuccessHandler)
                .and().oauth2Client()
                .and().exceptionHandling()
                .accessDeniedPage("/403");

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
