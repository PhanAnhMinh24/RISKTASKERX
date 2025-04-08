package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AddRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.role.IRoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointConstants.ROLES)
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(EndpointConstants.ADD)
    public ResponseEntity<ApiResult<RoleResponse>> addRole(@Valid @RequestBody AddRoleRequest request) {
        RoleResponse response = roleService.addRole(request);
        return ResponseEntity.ok(ApiResult.success(response));
    }
}
