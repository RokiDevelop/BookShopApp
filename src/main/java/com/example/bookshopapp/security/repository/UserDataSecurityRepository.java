package com.example.bookshopapp.security.repository;

import com.example.bookshopapp.security.data.AuthenticationType;
import com.example.bookshopapp.security.data.UserDataSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataSecurityRepository extends JpaRepository<UserDataSecurity, Integer> {
    UserDataSecurity findUserDataSecurityByEmail(String email);

    UserDataSecurity findUserDataSecurityByPhone(String phone);

    @Modifying
    @Query("UPDATE UserDataSecurity u SET u.authType = ?2 WHERE u.email = ?1")
    Optional<UserDataSecurity> updateAuthenticationType(String userEmail, AuthenticationType authType);

    @Override
    Optional<UserDataSecurity> findById(Integer integer);
}
