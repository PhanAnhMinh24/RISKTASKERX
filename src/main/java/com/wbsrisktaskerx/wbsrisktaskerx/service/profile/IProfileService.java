package com.wbsrisktaskerx.wbsrisktaskerx.service.profile;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ProfileResponse;

public interface IProfileService {
    ProfileResponse getProfileByEmail(String email);
}