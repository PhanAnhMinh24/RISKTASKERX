package com.wbsrisktaskerx.wbsrisktaskerx.service.role;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AddRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;

public interface IRoleService {
    RoleResponse addRole(AddRoleRequest request);
}
