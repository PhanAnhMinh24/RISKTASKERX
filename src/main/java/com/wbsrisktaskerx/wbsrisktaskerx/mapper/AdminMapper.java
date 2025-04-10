package com.wbsrisktaskerx.wbsrisktaskerx.mapper;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;

public class AdminMapper {
    public static AdminResponse adminMapper(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .role(admin.getRole())
                .departmentName(admin.getDepartmentName())
                .lastLogin(admin.getLastLogin())
                .isActive(admin.getIsActive())
                .build();
    }
}
