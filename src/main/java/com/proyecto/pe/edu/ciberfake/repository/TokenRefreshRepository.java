package com.proyecto.pe.edu.ciberfake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.pe.edu.ciberfake.model.TokenRefresh;

import java.util.Optional;

@Repository
public interface TokenRefreshRepository extends JpaRepository<TokenRefresh, Long> {
    Optional<TokenRefresh> findByTokrToken(String tokrToken);

    void deleteByTokrToken(String tokrToken);
}
