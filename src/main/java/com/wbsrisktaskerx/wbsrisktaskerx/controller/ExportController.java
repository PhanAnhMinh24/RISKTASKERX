package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.export.IExportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public ResponseEntity<InputStreamResource> download(@RequestBody PagingRequest<SearchFilterCustomersRequest> request) throws IOException {
        ExportCustomerResponse exportResponse = exportService.getCustomerList(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ExportConstants.HEADER_VALUE + exportResponse.getFileName())
                .contentType(MediaType.parseMediaType(ExportConstants.MS_EXCEL))
                .body(exportResponse.getResponse());
    }
}
