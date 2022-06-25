package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.proyecto.pe.edu.ciberfake.exceptions.CiberFakeException;
import com.proyecto.pe.edu.ciberfake.model.TokenRefresh;
import com.proyecto.pe.edu.ciberfake.repository.TokenRefreshRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class TokenRefreshService {

    private final TokenRefreshRepository tokenRefreshRepository;

    public TokenRefresh generarTokenRefresh(){
        TokenRefresh tokenRefresh = new TokenRefresh();
        tokenRefresh.setTokrToken(UUID.randomUUID().toString());
        tokenRefresh.setTokrFecCrea(Instant.now());

        return tokenRefreshRepository.save(tokenRefresh);
    }

    void validarTokenRefresh(String token){
        tokenRefreshRepository.findByTokrToken(token)
                .orElseThrow(() -> new CiberFakeException("RefreshToken invalido"));
    }

    public void borrarTokenRefresh(String token){
        tokenRefreshRepository.deleteByTokrToken(token);
    }
}
