package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.ExcelUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExportService implements IExportService{
    private final CustomerJpaQueryRepository customerJpaQueryRepository;

    public ExportService (CustomerJpaQueryRepository customerJpaQueryRepository){
        this.customerJpaQueryRepository =customerJpaQueryRepository;
    }

    @Override
    public ExportCustomerResponse getCustomerList() throws IOException {
        ByteArrayInputStream inputStream = ExcelUtils.customerToExcel(customerJpaQueryRepository.getAll());
        InputStreamResource response = new InputStreamResource(inputStream);

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(ExportConstants.DATE_TIME));
        String fileName = ExportConstants.FILENAME + currentDate + ExportConstants.XLSX;

        return new ExportCustomerResponse(response, fileName);
    }
}
