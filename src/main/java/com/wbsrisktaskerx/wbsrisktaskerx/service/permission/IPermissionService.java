package com.wbsrisktaskerx.wbsrisktaskerx.service.permission;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.PermissionRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPermissionService {
    Permission createPermission(PermissionRequest request);
    Page<Permission> getAllPermissions(PagingRequest<?> request);
    Permission getPermissionById(Integer id);
    Permission updatePermission(Integer id, PermissionRequest request);
    void deletePermission(Integer id);
}
