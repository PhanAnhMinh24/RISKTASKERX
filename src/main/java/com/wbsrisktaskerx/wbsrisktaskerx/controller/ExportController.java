package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;
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
    public ResponseEntity<ExportCustomerResponse> download(@RequestBody PagingRequest<SearchFilterCustomersRequest> request) throws IOException {
        ExportCustomerResponse exportResponse = exportService.getCustomerList(request);
        return ResponseEntity.ok().body(exportResponse);
    }

    @GetMapping(EndpointConstants.CUSTOMERS + EndpointConstants.PURCHASE + EndpointConstants.ID)
    public ResponseEntity<ExportCustomerResponse> exportPurchaseHistory(@PathVariable int id) throws IOException {
        ExportCustomerResponse exportResponse = exportService.exportCustomerPurchaseHistory(id);
        return ResponseEntity.ok().body(exportResponse);
    }

    @GetMapping(EndpointConstants.CUSTOMERS + EndpointConstants.WARRANTY + EndpointConstants.ID)
    public ResponseEntity<ExportCustomerResponse> exportWarrantyHistory(@PathVariable int id) throws IOException {
        ExportCustomerResponse exportResponse = exportService.exportCustomerWarrantyHistory(id);
        return ResponseEntity.ok().body(exportResponse);
    }
}
