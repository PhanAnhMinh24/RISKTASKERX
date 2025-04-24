package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointUtil;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ForgotPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ResetPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.VerifyOtpResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.admin.AdminService;
import com.wbsrisktaskerx.wbsrisktaskerx.service.otp.AdminEmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(EndpointConstants.ADMIN)
public class AdminController {

    private final AdminEmailService adminEmailService;
    private final AdminService adminService;

    public AdminController(AdminEmailService adminEmailService, AdminService adminService) {
        this.adminEmailService = adminEmailService;
        this.adminService = adminService;
    }

    @GetMapping(EndpointUtil.OTP + EndpointUtil.EMAIL + EndpointUtil.SEND)
    public ResponseEntity<ApiResult<Boolean>> sendOtpEmail(@RequestParam String to) throws MessagingException, IOException {
        return ResponseEntity.ok(ApiResult.success(adminEmailService.sendOtpEmail(to)));
    }

    @PostMapping(EndpointUtil.OTP + EndpointUtil.EMAIL + EndpointUtil.VERIFY)
    public ResponseEntity<ApiResult<VerifyOtpResponse>> verifyOtp(@RequestBody @Valid ForgotPasswordRequest request) {
        return ResponseEntity.ok(ApiResult.success(adminEmailService.verifyOtp(request)));
    }

    @PutMapping(EndpointUtil.OTP + EndpointUtil.EMAIL + EndpointUtil.FORGOT_PASSWORD)
    public ResponseEntity<ApiResult<Boolean>> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(ApiResult.success(adminEmailService.resetPassword(request)));
    }

    @PostMapping(EndpointConstants.SEARCH_FILTER)
    public ResponseEntity<ApiResult<Page<AdminResponse>>> searchFilterAdmin(@RequestBody PagingRequest<SearchFilterAdminRequest> request) {
        Page<AdminResponse> pageResult = adminService.searchAndFilterAdmin(request);
        return ResponseEntity.ok(ApiResult.success(pageResult));
    }

    @PostMapping(EndpointConstants.SEARCH_FILTER + "/no-paging")
    public ResponseEntity<ApiResult<List<AdminResponse>>> searchFilterAdminNoPaging(@RequestBody @Valid SearchFilterAdminRequest request) {
        List<AdminResponse> result = adminService.searchedAndFilteredAdminNoPaging(request);
        return ResponseEntity.ok(ApiResult.success(result));
    }

    @PostMapping
    public ResponseEntity<ApiResult<AdminResponse>> addAdmin(@Valid @RequestBody AdminRequest request) {
        AdminResponse response = adminService.addAdmin(request);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<AdminResponse>> getAdminDetail(@PathVariable int id) {
        return ResponseEntity.ok(ApiResult.success(adminService.getAdminById(id)));
    }

    @PutMapping()
    public ResponseEntity<ApiResult<Boolean>> update(@RequestBody AdminRequest request) {
        return ResponseEntity.ok().body(ApiResult.success(adminService.updateAdmin(request)));
    }

    @GetMapping(EndpointUtil.TEMPORARY_PASSWORD + EndpointUtil.EMAIL + EndpointUtil.SEND)
    public ResponseEntity<ApiResult<Boolean>> sendTemporaryPassword(@RequestParam String to) throws MessagingException, IOException {
        return ResponseEntity.ok(ApiResult.success(adminEmailService.sendTemporaryPassword(to)));
    }
}
