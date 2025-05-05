package com.wbsrisktaskerx.wbsrisktaskerx.mapper;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SignupRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public static AdminResponse adminMapper(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .phoneNumber(admin.getPhoneNumber())
                .role(RoleMapper.roleMapper(admin.getRole()))
                .departmentName(admin.getDepartmentName())
                .lastLogin(admin.getLastLogin())
                .isActive(admin.getIsActive())
                .dateOfBirth(admin.getDateOfBirth())
                .profileImg(admin.getProfileImg())
                .build();
    }

    public static Admin adminMapperByAdminRequest(AdminRequest request, String password, Role role) {
        return Admin.builder()
                .fullName(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(new BCryptPasswordEncoder().encode(password))
                .isActive(false)
                .departmentName(request.getDepartmentName())
                .role(role)
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

    public static Admin getAdminBySignUpRequest(SignupRequest signupRequest) {
        return Admin.builder()
                .fullName(signupRequest.getFullName())
                .email(signupRequest.getEmail())
                .phoneNumber(signupRequest.getPhoneNumber())
                .password(new BCryptPasswordEncoder().encode(signupRequest.getPassword()))
                .profileImg(signupRequest.getProfileImg())
                .roleId(signupRequest.getRoleId())
                .isActive(false)
                .departmentName(signupRequest.getDepartmentName())
                .build();
    }
}
