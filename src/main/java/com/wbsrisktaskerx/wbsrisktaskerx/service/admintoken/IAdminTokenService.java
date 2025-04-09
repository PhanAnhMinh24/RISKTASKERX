package com.wbsrisktaskerx.wbsrisktaskerx.service.admintoken;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.AdminToken;

public interface IAdminTokenService {
    AdminToken saveToken(Admin admin, String token);
}
