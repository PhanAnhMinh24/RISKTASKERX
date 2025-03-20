package com.wbsrisktaskerx.wbsrisktaskerx.service.profile;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ProfileResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileResponse getProfileByEmail(String email) {
        Admin admin = profileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return ProfileResponse.builder()
                .id(admin.getId().longValue())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .phoneNumber(admin.getPhoneNumber())
                .profileImg(admin.getProfileImg())
                .isActive(admin.getIsActive())
                .build();
    }
}
