package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.AdminToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AdminTokenRepository extends JpaRepository<AdminToken, Integer> {
    Optional<AdminToken> findByAccessToken(String token);
}
