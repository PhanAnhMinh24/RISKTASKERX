package com.wbsrisktaskerx.wbsrisktaskerx.service.role;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.RolePermission;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ActiveRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.RoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PermissionResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleJpaQueryRepository roleJpaQueryRepository;
    private final EntityManager entityManager;

    public RoleService (RoleRepository roleRepository, PermissionRepository permissionRepository,
                        RolePermissionRepository rolePermissionRepository, RoleJpaQueryRepository roleJpaQueryRepository,
                        EntityManager entityManager) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleJpaQueryRepository = roleJpaQueryRepository;
        this.entityManager = entityManager;
    }

    private Role findById(Integer id){
        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()){
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        return role.get();
    }

    @Override
    @Transactional
    public RoleResponse addRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_ALREADY_EXISTS);
        }

        Role role = roleRepository.save(Role.builder()
                .name(request.getName())
                .isActive(request.getIsActive())
                .build());

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissionId());
        if (permissions.size() != request.getPermissionId().size()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        List<RolePermission> rolePermissions = permissions.stream()
                .map(permission -> RolePermission.builder()
                        .role(role)
                        .permission(permission)
                        .build())
                .collect(Collectors.toList());

        rolePermissionRepository.saveAll(rolePermissions);

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .isActive(role.getIsActive())
                .updateAt(role.getUpdateAt())
                .build();
    }

    @Override
    @jakarta.transaction.Transactional
    public boolean updateIsActive(ActiveRoleRequest request) {
        Role role = findById(request.getId());
        role.setIsActive(request.getIsActive());
        roleRepository.save(role);
        return Boolean.TRUE;
    }

    @Override
    public Page<RoleResponse> searchAndFilterRole(PagingRequest<SearchFilterRoleRequest> request) {
        return roleJpaQueryRepository.searchedAndFilteredRole(request);
    }

    @Override
    @Transactional
    public RoleResponse getRoleById(int id) {
        Role role = findById(id);
        List<RolePermission> rolePermissions = role.getRolePermissions();
        List<PermissionResponse> permissions = rolePermissions.stream()
                .map(rolePermission -> new PermissionResponse(
                        rolePermission.getPermission().getId(),
                        rolePermission.getPermission().getKey(),
                        rolePermission.getPermission().getName()))
                .toList();
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getIsActive(),
                role.getUpdateAt(),
                permissions
        );
    }

    @Override
    @Transactional
    public RoleResponse updateRole(int id, RoleRequest request) {
        Role role = findById(id);

        Optional.ofNullable(request.getName())
                .map(String::trim)
                .filter(name -> !name.isEmpty() && !name.equals(role.getName()))
                .ifPresent(name -> {
                    roleRepository.findByName(name)
                            .filter(r -> !r.getId().equals(id))
                            .ifPresent(r -> { throw new AppException(ErrorCode.ROLE_NAME_EXISTS); });
                    role.setName(name);
                });

        Optional.ofNullable(request.getIsActive()).ifPresent(role::setIsActive);

        Optional.ofNullable(request.getPermissionId())
                .filter(newPermissionId -> !newPermissionId.isEmpty())
                .ifPresent(newPermissionIds -> {
                    List<RolePermission> RolePermissions = rolePermissionRepository.findByRoleId(id);
                    Set<Integer> PermissionId = RolePermissions.stream()
                            .map(rp -> rp.getPermission().getId())
                            .collect(Collectors.toSet());

                    Set<Integer> updatedPermissionId = newPermissionIds.stream()
                            .filter(pid -> pid > 0)
                            .collect(Collectors.toSet());

                    List<Integer> permissionIdToDelete = PermissionId.stream()
                            .filter(pid -> !updatedPermissionId.contains(pid))
                            .toList();

                    List<Integer> permissionIdToAdd = updatedPermissionId.stream()
                            .filter(pid -> !PermissionId.contains(pid))
                            .toList();

                    if (!permissionIdToDelete.isEmpty()) {
                        List<RolePermission> rolePermissionsToDelete = rolePermissionRepository.findByRoleId(id).stream()
                                .filter(rp -> permissionIdToDelete.contains(rp.getPermission().getId()))
                                .collect(Collectors.toList());

                        if (!rolePermissionsToDelete.isEmpty()) {
                            rolePermissionRepository.deleteAllInBatch(rolePermissionsToDelete);
                            rolePermissionRepository.flush();
                            entityManager.clear();
                        }
                    }

                    if (!permissionIdToAdd.isEmpty()) {
                        List<RolePermission> newRolePermissions = permissionIdToAdd.stream()
                                .map(permissionId -> RolePermission.builder()
                                        .role(role)
                                        .permission(permissionRepository.findById(permissionId)
                                                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)))
                                        .build())
                                .collect(Collectors.toList());

                        try {
                            rolePermissionRepository.saveAll(newRolePermissions);
                            rolePermissionRepository.flush();
                        } catch (Exception e) {
                            throw new AppException(ErrorCode.ROLE_PERMISSION_DUPLICATE);
                        }
                    }
                });

        List<PermissionResponse> permissions = rolePermissionRepository.findByRoleId(id).stream()
                .map(rp -> new PermissionResponse(
                        rp.getPermission().getId(),
                        rp.getPermission().getKey(),
                        rp.getPermission().getName()))
                .toList();

        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getIsActive(),
                role.getUpdateAt(),
                permissions
        );
    }
}
