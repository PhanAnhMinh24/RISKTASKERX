package com.wbsrisktaskerx.wbsrisktaskerx.service.auth;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.*;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.JwtResponse;

public interface IAuthService {
    JwtResponse login(LoginRequest loginRequest);
    JwtResponse signup(SignupRequest signupRequest);
    Boolean changePassword(ChangePasswordRequest changePasswordRequest);
    Boolean activateAccount(ActiveAdminRequest request);
}
