package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportResponse;

import java.io.IOException;

public interface IExportService {
    ExportResponse getCustomerList(PagingRequest<SearchFilterCustomersRequest> request) throws IOException;

    ExportResponse getAdminList(PagingRequest<SearchFilterAdminRequest> request) throws IOException;

    ExportResponse exportCustomerPurchaseHistory(Integer id) throws IOException;

    ExportResponse exportCustomerWarrantyHistory(Integer customerId) throws IOException;
}