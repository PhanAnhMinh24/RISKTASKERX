package com.wbsrisktaskerx.wbsrisktaskerx.service.admin;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import org.springframework.data.domain.Page;

public interface IAdminService {
    Page<AdminResponse> searchAndFilterAdmin(PagingRequest<SearchFilterAdminRequest> request);
    AdminResponse addAdmin(AdminRequest request);
}
