package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportRoleResponse;

import java.io.IOException;

public interface IExportService {
    ExportCustomerResponse getCustomerList(PagingRequest<SearchFilterCustomersRequest> request) throws IOException;
    ExportRoleResponse getRoleList(PagingRequest<SearchFilterRoleRequest> request) throws IOException;
    ExportCustomerResponse exportCustomerPurchaseHistory(Integer customerId) throws IOException;
    ExportCustomerResponse exportCustomerWarrantyHistory(Integer customerId) throws IOException;
}