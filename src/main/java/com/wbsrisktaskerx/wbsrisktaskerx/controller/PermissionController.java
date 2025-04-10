package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Permission;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PermissionResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.permission.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.wbsrisktaskerx.wbsrisktaskerx.common.constants.MessageConstants.GET_ALL_PERMISSIONS_SUCCESS;

@RestController
@RequestMapping(EndpointConstants.PERMISSION)
@RequiredArgsConstructor
public class PermissionController {

    private final IPermissionService permissionService;
    @GetMapping()
    public ApiResult<List<PermissionResponse>> getAllPermissionTree() {
        List<PermissionResponse> list = permissionService.getAllPermissionsWithChildren();
        return ApiResult.success(list, GET_ALL_PERMISSIONS_SUCCESS);
    }

}
