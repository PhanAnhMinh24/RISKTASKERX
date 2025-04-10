package com.wbsrisktaskerx.wbsrisktaskerx.service.permission;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;

import java.util.List;

public interface IPermissionService {
    List<Permission> getAllPermissions();
    Permission getPermissionById(Integer id);
}
