package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;

import java.io.IOException;

public interface IExportService {
    ExportCustomerResponse getCustomerList(PagingRequest<SearchFilterCustomersRequest> request) throws IOException;
    ExportCustomerResponse exportCustomerPurchaseHistory(Integer customerId, Integer paymentId) throws IOException;
    ExportCustomerResponse exportCustomerWarrantyHistory(Integer customerId) throws IOException;
}