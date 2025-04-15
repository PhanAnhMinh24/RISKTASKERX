package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.MessageConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ActiveAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ChangePasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.LoginRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SignupRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.JwtResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.auth.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wbsrisktaskerx.wbsrisktaskerx.common.constants.MessageConstants.ACCOUNT_ACTIVATED_SUCCESSFULLY;
import static com.wbsrisktaskerx.wbsrisktaskerx.common.constants.MessageConstants.ACCOUNT_ACTIVATION_FAILED;

@RestController
@RequestMapping(EndpointConstants.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping(EndpointConstants.SIGN_IN)
    public ResponseEntity<ApiResult<JwtResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResult.success(jwtResponse));
    }

    @PostMapping(EndpointConstants.SIGN_UP)
    public ResponseEntity<ApiResult<JwtResponse>> registerUser(@RequestBody SignupRequest signupRequest) {
        JwtResponse jwtResponse = authService.signup(signupRequest);
        return ResponseEntity.ok(ApiResult.success(jwtResponse));
    }

    @PutMapping(EndpointConstants.CHANGE_PASSWORD)
    public ResponseEntity<ApiResult<Boolean>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Boolean result = authService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(ApiResult.success(result));
    }

    @PostMapping(EndpointConstants.ACTIVE)
    public ResponseEntity<ApiResult<String>> activateAccount(@RequestBody ActiveAdminRequest request) {
        Boolean result = authService.activateAccount(request);
        String message = result
                ? (request.getIsActive() ? MessageConstants.ACCOUNT_ACTIVATED_SUCCESSFULLY : MessageConstants.ACCOUNT_DEACTIVATED_SUCCESSFULLY)
                : MessageConstants.ACCOUNT_ACTIVATION_FAILED;

        return ResponseEntity.ok(ApiResult.success(message));
    }

}
