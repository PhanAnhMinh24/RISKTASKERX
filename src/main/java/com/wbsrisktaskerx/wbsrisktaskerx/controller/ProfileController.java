package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
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
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }
        String token = authHeader.substring(7);
        String email = jwtUtils.getUserNameFromJwtToken(token);
        ProfileResponse response = profileService.getProfileByEmail(email);
        return ApiResult.success(response);
    }
}
