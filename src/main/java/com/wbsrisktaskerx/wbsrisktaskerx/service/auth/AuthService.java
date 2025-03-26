package com.wbsrisktaskerx.wbsrisktaskerx.service.auth;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ChangePasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.LoginRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SignupRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.JwtResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.JwtUtils;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PasswordUtils;
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
        if (!signupRequest.getEmail().matches(EmailConstants.EMAIL_REGEX)) {
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
    public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = JwtUtils.getCurrentAdmin();
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordUtils.validatePassword(passwordEncoder, changePasswordRequest, admin.getPassword());
        admin.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        adminRepository.save(admin);
        return Boolean.TRUE;
    }


}
