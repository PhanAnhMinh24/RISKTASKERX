package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    List<RolePermission> findByRoleId(Integer roleId);
    void deleteByRoleIdAndPermissionId(Integer roleId, Integer permissionId);
    List<RolePermission> findByRoleIdAndPermissionIdIn(Integer roleId, List<Integer> permissionIds);
}
