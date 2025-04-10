package com.wbsrisktaskerx.wbsrisktaskerx.service.permission;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PermissionResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;
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
            if (p.getParentId() == null) {
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

}
