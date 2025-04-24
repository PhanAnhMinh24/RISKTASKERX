package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ExportHistoryRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.export.IExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(EndpointConstants.EXPORT)
public class ExportController {

    private final IExportService exportService;

    public ExportController(IExportService exportService) {
        this.exportService = exportService;
    }

    @PostMapping(EndpointConstants.CUSTOMERS)
    public ResponseEntity<ExportResponse> download(@RequestBody PagingRequest<SearchFilterCustomersRequest> request) throws IOException {
        ExportResponse exportResponse = exportService.getCustomerList(request);
        return ResponseEntity.ok().body(exportResponse);
    }

    @PostMapping(EndpointConstants.ADMIN)
    public ResponseEntity<ExportResponse> downloadAdmin(@RequestBody PagingRequest<SearchFilterAdminRequest> request) throws IOException {
        ExportResponse exportResponse = exportService.getAdminList(request);
        return ResponseEntity.ok().body(exportResponse);
    }

    @GetMapping(EndpointConstants.CUSTOMERS + EndpointConstants.PURCHASE + EndpointConstants.ID)
    public ResponseEntity<ExportResponse> exportPurchaseHistory(@PathVariable int id) throws IOException {
        ExportResponse exportResponse = exportService.exportCustomerPurchaseHistory(id);
        return ResponseEntity.ok().body(exportResponse);
    }

    @GetMapping(EndpointConstants.CUSTOMERS + EndpointConstants.WARRANTY + EndpointConstants.ID)
    public ResponseEntity<ExportResponse> exportWarrantyHistory(@PathVariable int id) throws IOException {
        ExportResponse exportResponse = exportService.exportCustomerWarrantyHistory(id);
        return ResponseEntity.ok().body(exportResponse);
    }
}
