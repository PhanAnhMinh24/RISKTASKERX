package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ActiveRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.RoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.role.IRoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstants.ROLES)
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<ApiResult<RoleResponse>> addRole(@Valid @RequestBody RoleRequest request) {
        RoleResponse response = roleService.addRole(request);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PutMapping(EndpointConstants.STATUS)
    public ResponseEntity<ApiResult<Boolean>> updateRoleActive(@RequestBody ActiveRoleRequest request) {
        boolean result = roleService.updateIsActive(request);
        return ResponseEntity.ok(ApiResult.success(result));
    }

    @PostMapping(EndpointConstants.SEARCH_FILTER)
    public ResponseEntity<ApiResult<Page<RoleResponse>>> searchFilterRole(@RequestBody PagingRequest<SearchFilterRoleRequest> request) {
        Page<RoleResponse> pageResult = roleService.searchAndFilterRole(request);
        return ResponseEntity.ok(ApiResult.success(pageResult));
    }

    @GetMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<RoleResponse>> getRoleDetail(@PathVariable int id) {
        return ResponseEntity.ok(ApiResult.success(roleService.getRoleById(id)));
    }

    @PutMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<Boolean>> updateRole(@PathVariable int id, @RequestBody RoleRequest request) {
        return ResponseEntity.ok(ApiResult.success(roleService.updateRole(id, request)));
    }

}
