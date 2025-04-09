package com.wbsrisktaskerx.wbsrisktaskerx.service.admintoken;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.AdminToken;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AdminTokenService implements IAdminTokenService {

    final AdminTokenRepository adminTokenRepository;
    public AdminTokenService(AdminTokenRepository adminTokenRepository) {
        this.adminTokenRepository = adminTokenRepository;
    }

    @Override
    public AdminToken saveToken(Admin admin, String token) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime expiresAt = now.plusHours(24);
        String refreshToken = UUID.randomUUID().toString();

        AdminToken adminToken = AdminToken.builder()
                .admin(admin)
                .accessToken(token)
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .build();

        return adminTokenRepository.save(adminToken);
    }
}
