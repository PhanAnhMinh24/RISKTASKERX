package com.wbsrisktaskerx.wbsrisktaskerx.service.auth;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
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
                .orElseThrow(() -> new RuntimeException(ErrorCode.INVALID_USERNAME_OR_PASSWORD.getMessage()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            throw new RuntimeException(ErrorCode.INVALID_USERNAME_OR_PASSWORD.getMessage());
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
        // Kiểm tra định dạng email
        if (!signupRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException(ErrorCode.INVALID_REQUEST.getMessage());
        }

        if (adminRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException(ErrorCode.EMAIL_EXIST.getMessage());
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
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), admin.getPassword())) {
            throw new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), admin.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_MATCHES_OLD_PASSWORD, "New password cannot be the same as the old password.");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORDS_NOT_MATCH);
        }

        String newPassword = changePasswordRequest.getNewPassword();
        if (newPassword.length() < 8) {
            throw new AppException(ErrorCode.PASSWORD_TOO_SHORT);
        }
        if (!newPassword.matches(".*[A-Z].*")) {
            throw new AppException(ErrorCode.PASSWORD_NO_UPPERCASE);
        }
        if (!newPassword.matches(".*[0-9].*")) {
            throw new AppException(ErrorCode.PASSWORD_NO_NUMBER);
        }
        if (!newPassword.matches(".*[@#$%^&+=!].*")) {
            throw new AppException(ErrorCode.PASSWORD_NO_SPECIAL_CHAR);
        }
        if (newPassword.contains(" ")) {
            throw new AppException(ErrorCode.PASSWORD_CONTAINS_SPACE);
        }

        // Cập nhật mật khẩu mới
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);

        return "Password changed successfully";
    }

}
