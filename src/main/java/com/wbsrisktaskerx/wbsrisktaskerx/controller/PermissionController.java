package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.PermissionRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.service.permission.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wbsrisktaskerx.wbsrisktaskerx.common.constants.MessageConstants.*;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final IPermissionService permissionService;

    @PostMapping
    public ApiResult<Permission> create(@RequestBody PermissionRequest request) {
        Permission created = permissionService.createPermission(request);
        return ApiResult.success(created, CREATE_PERMISSION_SUCCESS);
    }

    @PostMapping("/list")
    public ApiResult<Page<Permission>> getAllWithPaging(@RequestBody PagingRequest<?> request) {
        Page<Permission> permissions = permissionService.getAllPermissions(request);
        return ApiResult.success(permissions, GET_ALL_PERMISSIONS_SUCCESS);
    }

    @GetMapping("/{id}")
    public ApiResult<Permission> getById(@PathVariable Integer id) {
        Permission permission = permissionService.getPermissionById(id);
        return ApiResult.success(permission, GET_PERMISSION_SUCCESS);
    }

    @PutMapping("/{id}")
    public ApiResult<Permission> update(@PathVariable Integer id, @RequestBody PermissionRequest request) {
        Permission updated = permissionService.updatePermission(id, request);
        return ApiResult.success(updated, UPDATE_PERMISSION_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable Integer id) {
        permissionService.deletePermission(id);
        return ApiResult.success(null, DELETE_PERMISSION_SUCCESS);
    }
}
