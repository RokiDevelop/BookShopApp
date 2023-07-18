package com.example.bookshopapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataSecurityService {
    private final UserDataSecurityRepository userDataSecurityRepository;

    @Autowired
    public UserDataSecurityService(UserDataSecurityRepository userDataSecurityRepository) {
        this.userDataSecurityRepository = userDataSecurityRepository;
    }

    public UserDataSecurity findUserDataSecurityByEmail(String email) {
        return userDataSecurityRepository.findUserDataSecurityByEmail(email);
    }

    public void save(UserDataSecurity user) {
        userDataSecurityRepository.save(user);
    }

    public UserDataSecurity findUserDataSecurity(Integer id) {
        Optional<UserDataSecurity> userDataSecurityOptional = userDataSecurityRepository.findById(id);
        return userDataSecurityOptional.orElseThrow();
    }
}
