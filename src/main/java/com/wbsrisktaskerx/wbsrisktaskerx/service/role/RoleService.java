package com.wbsrisktaskerx.wbsrisktaskerx.service.role;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.RolePermission;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.RoleMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ActiveRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.RoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ActiveRoleResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PermissionResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.*;
import com.wbsrisktaskerx.wbsrisktaskerx.service.permission.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleJpaQueryRepository roleJpaQueryRepository;
    private final AdminRepository adminRepository;
    private final PermissionService permissionService;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository,
                       RolePermissionRepository rolePermissionRepository, RoleJpaQueryRepository roleJpaQueryRepository,
                       PermissionService permissionService, AdminRepository adminRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleJpaQueryRepository = roleJpaQueryRepository;
        this.permissionService = permissionService;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<ActiveRoleResponse> getAllActiveRole() {
        List<Role> activeRoles = roleRepository.findByIsActiveTrue();
        if(activeRoles.isEmpty()) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        return activeRoles.stream()
                .map(r -> new ActiveRoleResponse(r.getId(), r.getName()))
                .toList();
    }

    @Override
    public Role findById(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        return role.get();
    }

    @Override
    @Transactional
    public RoleResponse addRole(RoleRequest request) {
        String name = Optional.ofNullable(request.getName())
                .map(String::trim)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE_NAME));

        if (name.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_ROLE_NAME);
        }

        if (roleRepository.existsByName(name)) {
            throw new AppException(ErrorCode.ROLE_ALREADY_EXISTS);
        }

        Role role = roleRepository.save(Role.builder()
                .name(name)
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

        return RoleMapper.roleMapper(role);
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
    public boolean updateRole(RoleRequest request) {
        Role role = findById(request.getId());

        Optional.ofNullable(request.getName())
                .map(String::trim)
                .ifPresent(name -> {
                    if (name.isEmpty()) {
                        throw new AppException(ErrorCode.INVALID_VALID_NAME);
                    }

                    if (!name.equalsIgnoreCase(role.getName()) &&
                            roleRepository.existsByName(name)) {
                        throw new AppException(ErrorCode.ROLE_NAME_EXISTS);
                    }

                    role.setName(name);
                });

        Optional.ofNullable(request.getPermissionId())
                .filter(ids -> !ids.isEmpty())
                .ifPresent(ids -> permissionService.updateRolePermissions(role, ids));
        return true;
    }

    @Override
    @Transactional
    public boolean deleteRole(Integer id) {
        Role role = findById(id);

        if (adminRepository.existsByRole(role)) {
            throw new AppException(ErrorCode.ROLE_IN_USE_BY_ADMIN);
        }

        rolePermissionRepository.deleteByRole(role);
        roleRepository.delete(role);
        return true;
    }
}
