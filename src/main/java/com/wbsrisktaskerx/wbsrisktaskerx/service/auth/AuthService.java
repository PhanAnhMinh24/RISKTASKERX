package com.wbsrisktaskerx.wbsrisktaskerx.service.auth;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ChangePasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.LoginRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SignupRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.JwtResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AdminRepository adminRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtils.generateToken(admin.getEmail());

        return new JwtResponse(
                token,
                ObjectUtils.isEmpty(admin.getId()) ? null : admin.getId().longValue(),
                admin.getFullName(),
                admin.getEmail()
        );
    }

    @Override
    public JwtResponse signup(SignupRequest signupRequest) {
        if (!signupRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException("Invalid email format");
        }

        if (adminRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        Admin newAdmin = Admin.builder()
                .fullName(signupRequest.getFullName())
                .email(signupRequest.getEmail())
                .phoneNumber(signupRequest.getPhoneNumber())
                .password(encodedPassword)
                .profileImg(signupRequest.getProfileImg())
                .roleId(signupRequest.getRoleId())
                .isActive(true)
                .build();

        Admin savedAdmin = adminRepository.save(newAdmin);
        String token = jwtUtils.generateToken(savedAdmin.getEmail());

        return new JwtResponse(
                token,
                savedAdmin.getId() != null ? savedAdmin.getId().longValue() : null,
                savedAdmin.getFullName(),
                savedAdmin.getEmail()
        );
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = JwtUtils.getCurrentAdmin();
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), admin.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new RuntimeException("New passwords do not match");
        }

        String newPassword = changePasswordRequest.getNewPassword();
        String passwordRegex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        if (!newPassword.matches(passwordRegex)) {
            throw new RuntimeException("New password must be at least 8 characters long, contain at least one special character, one number, one uppercase letter, and have no spaces.");
        }

        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);

        return "Password changed successfully";
    }

}
