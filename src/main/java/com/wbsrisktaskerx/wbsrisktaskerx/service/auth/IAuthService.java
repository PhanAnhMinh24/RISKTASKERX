package com.wbsrisktaskerx.wbsrisktaskerx.service.auth;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.LoginRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.JwtResponse;

public interface IAuthService {
    JwtResponse login(LoginRequest loginRequest);
}
