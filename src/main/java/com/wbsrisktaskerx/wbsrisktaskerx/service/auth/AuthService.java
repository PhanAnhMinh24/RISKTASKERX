package com.wbsrisktaskerx.wbsrisktaskerx.service.auth;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.LoginRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.JwtResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AdminRepository adminRepository;
    private final JwtUtils jwtUtils;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));


        if (!loginRequest.getPassword().equals(admin.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }


        String token = jwtUtils.generateToken(admin.getEmail());


        return new JwtResponse(token, admin.getId() != null ? admin.getId().longValue() : null, admin.getFullName());
    }
}
