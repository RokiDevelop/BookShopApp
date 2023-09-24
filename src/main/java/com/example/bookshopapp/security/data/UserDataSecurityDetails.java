package com.example.bookshopapp.security.data;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDataSecurityDetails implements UserDetails {

    private UserDataSecurity userDataSecurity;

    public UserDataSecurityDetails(UserDataSecurity userDataSecurity) {
        this.userDataSecurity = userDataSecurity;
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
            return userDataSecurity.getName();
    }

    public String getEmail() {
        return userDataSecurity.getEmail();
    }

    public String getPhone() {
        return userDataSecurity.getPhone();
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
