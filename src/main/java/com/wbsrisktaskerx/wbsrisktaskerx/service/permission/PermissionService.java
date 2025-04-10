package com.wbsrisktaskerx.wbsrisktaskerx.service.permission;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.PermissionRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.PermissionRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(PermissionRequest request) {
        Permission permission = Permission.builder()
                .key(request.getKey())
                .name(request.getName())
                .orderNumber(request.getOrderNumber())
                .parentId(request.getParentId())
                .build();
        return permissionRepository.save(permission);
    }

    @Override
    public Page<Permission> getAllPermissions(PagingRequest<?> request) {
        Pageable pageable = PageService.getPageRequest(request);
        return permissionRepository.findAll(pageable);
    }

    @Override
    public Permission getPermissionById(Integer id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
    }


    @Override
    public Permission updatePermission(Integer id, PermissionRequest request) {
        Permission existing = getPermissionById(id);
        existing.setKey(request.getKey());
        existing.setName(request.getName());
        existing.setOrderNumber(request.getOrderNumber());
        existing.setParentId(request.getParentId());
        return permissionRepository.save(existing);
    }

    @Override
    public void deletePermission(Integer id) {
        permissionRepository.deleteById(id);
    }
}
