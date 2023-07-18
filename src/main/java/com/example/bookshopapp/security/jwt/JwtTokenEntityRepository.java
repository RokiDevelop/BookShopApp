package com.example.bookshopapp.security.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JwtTokenEntityRepository extends JpaRepository<JwtTokenEntity, Long> {

    JwtTokenEntity getJwtTokenEntityByJwtString(String token);

    boolean existsByJwtString(String jwtString);
}
