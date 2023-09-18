package com.example.bookshopapp.security.data;

import com.example.bookshopapp.security.data.UserDataSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class UserDataSecurityDetails implements UserDetails {

    private final UserDataSecurity userDataSecurity;

    public UserDataSecurityDetails(UserDataSecurity userDataSecurity) {
        this.userDataSecurity = userDataSecurity;
    }

    public UserDataSecurity getUserDataSecurity() {
        return userDataSecurity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return userDataSecurity.getPassword();
    }

    @Override
    public String getUsername() {
        return userDataSecurity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
