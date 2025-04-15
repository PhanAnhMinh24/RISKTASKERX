package com.wbsrisktaskerx.wbsrisktaskerx.service.auth;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ActiveAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ChangePasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.LoginRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SignupRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.JwtResponse;

public interface IAuthService {
    JwtResponse login(LoginRequest loginRequest);

    JwtResponse signup(SignupRequest signupRequest);

    Boolean changePassword(ChangePasswordRequest changePasswordRequest);

    Boolean activateAccount(ActiveAdminRequest request);
}
