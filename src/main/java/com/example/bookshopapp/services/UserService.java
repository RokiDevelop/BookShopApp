package com.example.bookshopapp.services;

import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
