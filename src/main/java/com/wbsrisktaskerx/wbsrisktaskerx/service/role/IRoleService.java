package com.wbsrisktaskerx.wbsrisktaskerx.service.role;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ActiveRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.RoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ActiveRoleResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {
    RoleResponse addRole(RoleRequest request);

    boolean updateIsActive(ActiveRoleRequest request);

    Page<RoleResponse> searchAndFilterRole(PagingRequest<SearchFilterRoleRequest> request);

    RoleResponse getRoleById(int id);

    boolean updateRole(RoleRequest request);

    Role findById(Integer id);

    boolean deleteRole(Integer id);

    List<ActiveRoleResponse> getAllActiveRole();

}
