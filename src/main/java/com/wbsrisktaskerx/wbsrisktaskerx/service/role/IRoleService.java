package com.wbsrisktaskerx.wbsrisktaskerx.service.role;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ActiveRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.RoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;
import org.springframework.data.domain.Page;

public interface IRoleService {
    RoleResponse addRole(RoleRequest request);
    boolean updateIsActive(ActiveRoleRequest request);
    Page<RoleResponse> searchAndFilterRole(PagingRequest<SearchFilterRoleRequest> request);
    RoleResponse getRoleById(int id);
    boolean updateRole(int id, RoleRequest request);
}
