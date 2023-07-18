package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.book.links.User2UserDataSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface User2UserDataSecurityRepository extends JpaRepository<User2UserDataSecurity, Integer> {

    Optional<User2UserDataSecurity> findUser2UserDataSecurityByUserDataSecurityId(Integer userDataSecurity_id);
}
