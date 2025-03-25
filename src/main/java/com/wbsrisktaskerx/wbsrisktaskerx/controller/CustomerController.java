package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
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

    @GetMapping(EndpointConstants.EXPORT_CUSTOMER)
    public ResponseEntity<InputStreamResource> download() throws IOException {
        ByteArrayInputStream inputStream = exportService.getCustomerList();
        InputStreamResource response = new InputStreamResource(inputStream);

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        String fileName = ExportConstants.FILENAME + currentDate + ExportConstants.XLSX;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ExportConstants.HEADER_VALUE + fileName)
                .contentType(MediaType.parseMediaType(ExportConstants.MS_EXCEL))
                .body(response);
    }
}
