package com.wbsrisktaskerx.wbsrisktaskerx.service.permission;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.RolePermission;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PermissionResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.PermissionRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.RolePermissionRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final EntityManager entityManager;

    @Override
    public List<PermissionResponse> getAllPermissionsWithChildren() {
        List<Permission> allPermissions = permissionRepository.findAll();

        // Map ID -> PermissionResponse
        Map<Integer, PermissionResponse> map = new HashMap<>();
        List<PermissionResponse> roots = new ArrayList<>();

        for (Permission p : allPermissions) {
            PermissionResponse response = new PermissionResponse(
                    p.getId(),
                    p.getKey(),
                    p.getName(),
                    p.getOrderNumber(),
                    p.getParentId(),
                    new ArrayList<>()
            );
            map.put(p.getId(), response);
        }

        for (Permission p : allPermissions) {
            PermissionResponse current = map.get(p.getId());
            if (ObjectUtils.isEmpty(p.getParentId())) {
                roots.add(current);
            } else {
                PermissionResponse parent = map.get(p.getParentId());
                if (parent != null) {
                    parent.getChildren().add(current);
                }
            }
        }

        return roots;
    }

    @Override
    public void updateRolePermissions(int roleId, List<Integer> newPermissionIds) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
        Set<Integer> existingPermissionIds = rolePermissions.stream()
                .map(rp -> rp.getPermission().getId())
                .collect(Collectors.toSet());

        Set<Integer> updatedPermissionIds = newPermissionIds.stream()
                .filter(pid -> pid > 0)
                .collect(Collectors.toSet());

        List<Integer> permissionIdToDelete = existingPermissionIds.stream()
                .filter(pid -> !updatedPermissionIds.contains(pid))
                .toList();

        List<Integer> permissionIdToAdd = updatedPermissionIds.stream()
                .filter(pid -> !existingPermissionIds.contains(pid))
                .toList();

        if (!permissionIdToDelete.isEmpty()) {
            List<RolePermission> rolePermissionsToDelete = rolePermissions.stream()
                    .filter(rp -> permissionIdToDelete.contains(rp.getPermission().getId()))
                    .collect(Collectors.toList());
            rolePermissionRepository.deleteAllInBatch(rolePermissionsToDelete);
        }

        if (!permissionIdToAdd.isEmpty()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

            List<RolePermission> newRolePermissions = permissionIdToAdd.stream()
                    .map(pid -> RolePermission.builder()
                            .role(role)
                            .permission(permissionRepository.findById(pid)
                                    .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)))
                            .build())
                    .toList();

            rolePermissionRepository.saveAll(newRolePermissions);
        }

    }

    public List<PermissionResponse> getPermissionResponsesByRoleId(int roleId) {
        return rolePermissionRepository.findByRoleId(roleId).stream()
                .map(rp -> new PermissionResponse(
                        rp.getPermission().getId(),
                        rp.getPermission().getKey(),
                        rp.getPermission().getName()))
                .toList();
    }

}