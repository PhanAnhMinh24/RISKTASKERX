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
    // Nếu bạn không cần dùng PasswordEncoder, có thể loại bỏ nó.
    // private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        // Tìm Admin theo email
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // So sánh mật khẩu dưới dạng plaintext (không mã hóa)
        if (!loginRequest.getPassword().equals(admin.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Tạo JWT token (ở đây sử dụng email của admin làm subject)
        String token = jwtUtils.generateToken(admin.getEmail());

        // Trả về JwtResponse với token, id và userName (ở đây dùng fullName làm userName)
        return new JwtResponse(token, admin.getId() != null ? admin.getId().longValue() : null, admin.getFullName());
    }
}
