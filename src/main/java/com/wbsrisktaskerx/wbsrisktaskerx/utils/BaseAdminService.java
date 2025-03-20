package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ProfileResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BaseAdminService {
    private final AdminRepository adminRepository;

    public Admin getCurrentUser() {
        String email = JwtUtils.getCurrentAdmin();
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return admin.get();
    }

    public List<ProfileResponse> getAllProfile() {
        List<Admin> adminList = adminRepository.findAll();
        return adminList.stream()
                .map(admin -> ProfileResponse.builder()
                        .id(admin.getId() != null ? admin.getId().longValue() : null)
                        .fullName(admin.getFullName())
                        .email(admin.getEmail())
                        .phoneNumber(admin.getPhoneNumber())
                        .profileImg(admin.getProfileImg())
                        .isActive(admin.getIsActive())
                        .build())
                .collect(Collectors.toList());
    }




}
