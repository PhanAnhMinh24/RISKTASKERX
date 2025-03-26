package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ProfileResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.profile.IProfileService;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(EndpointConstants.PROFILE)
@RequiredArgsConstructor
public class ProfileController {
    private final IProfileService profileService;
    private final JwtUtils jwtUtils;
    @GetMapping
    public ApiResult<ProfileResponse> getProfile(HttpServletRequest request) {
        String authHeader = request.getHeader(CommonConstants.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(CommonConstants.BEARER)) {
            throw new AppException(ErrorCode.AUTHORIZATION_HEADER_IS_MISSING_OR_INVALID);
        }
        String token = authHeader.substring(7);
        String email = jwtUtils.getUserNameFromJwtToken(token);
        ProfileResponse response = profileService.getProfileByEmail(email);
        return ApiResult.success(response);
    }
}
