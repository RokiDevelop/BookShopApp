package com.example.bookshopapp.services;

import com.example.bookshopapp.data.book.links.User2UserDataSecurity;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.repositories.User2UserDataSecurityRepository;
import com.example.bookshopapp.security.UserDataSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class User2UserDataSecurityService {
    private final User2UserDataSecurityRepository user2UserDataSecurityRepository;

    @Autowired
    public User2UserDataSecurityService(User2UserDataSecurityRepository user2UserDataSecurityRepository) {
        this.user2UserDataSecurityRepository = user2UserDataSecurityRepository;
    }

    public UserEntity getUserByUserDataSecurityId(UserDataSecurity userDataSecurity) {
        Optional<User2UserDataSecurity> user2UserDataSecurity =
                user2UserDataSecurityRepository.findUser2UserDataSecurityByUserDataSecurityId(userDataSecurity.getId());
        return user2UserDataSecurity.orElseThrow().getUserEntity();
    }

    public void save(User2UserDataSecurity user2UserDataSecurity) {
        user2UserDataSecurityRepository.save(user2UserDataSecurity);
    }
}
