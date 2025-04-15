package com.wbsrisktaskerx.wbsrisktaskerx.service.permission;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    List<PermissionResponse> getAllPermissionsWithChildren();
    void updateRolePermissions(int roleId, List<Integer> newPermissionIds);
}
