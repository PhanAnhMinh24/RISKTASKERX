package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.export.IExportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(EndpointConstants.CUSTOMERS)
public class CustomerController {

    private final IExportService exportService;

    public CustomerController(IExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping(EndpointConstants.EXPORT + EndpointConstants.CUSTOMERS)
    public ResponseEntity<InputStreamResource> download() throws IOException {
        ExportCustomerResponse exportResponse = exportService.getCustomerList();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ExportConstants.HEADER_VALUE + exportResponse.getFileName())
                .contentType(MediaType.parseMediaType(ExportConstants.MS_EXCEL))
                .body(exportResponse.getResponse());
    }
}
