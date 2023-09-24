package com.example.bookshopapp.security.services;

import com.example.bookshopapp.security.data.UserDataSecurity;
import com.example.bookshopapp.security.repository.UserDataSecurityRepository;
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
    public UserDataSecurity findUserDataSecurityByPhone(String phone) {
        return userDataSecurityRepository.findUserDataSecurityByPhone(phone);
    }

    public UserDataSecurity save(UserDataSecurity user) {
        return userDataSecurityRepository.save(user);
    }

    public UserDataSecurity findUserDataSecurity(Integer id) {
        Optional<UserDataSecurity> userDataSecurityOptional = userDataSecurityRepository.findById(id);
        return userDataSecurityOptional.orElseThrow();
    }
}
