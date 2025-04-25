package com.wbsrisktaskerx.wbsrisktaskerx.mapper;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;

public class RoleMapper {

    public static RoleResponse roleMapper(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .isActive(role.getIsActive())
                .updateAt(role.getUpdateAt())
                .build();
    }
}
