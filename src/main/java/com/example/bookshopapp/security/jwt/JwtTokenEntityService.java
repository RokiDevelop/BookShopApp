package com.example.bookshopapp.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenEntityService {

    private final JwtTokenEntityRepository jwtTokenEntityRepository;

    @Autowired
    public JwtTokenEntityService(JwtTokenEntityRepository jwtTokenEntityRepository) {
        this.jwtTokenEntityRepository = jwtTokenEntityRepository;
    }

    public void blockToken(JwtTokenEntity tokenEntity) {
        String token = tokenEntity.getJwtString();
        if (token != null &&
                !token.equals("") &&
                !jwtTokenEntityRepository.existsByJwtString(token)) {
            jwtTokenEntityRepository.save(tokenEntity);
        }
    }

    public Boolean checkBlock(String token) {
        JwtTokenEntity jwtTokenEntity = jwtTokenEntityRepository.getJwtTokenEntityByJwtString(token);

        if (jwtTokenEntity == null) {
            return false;
        } else {
            return jwtTokenEntity.isBlocked();
        }
    }
}
