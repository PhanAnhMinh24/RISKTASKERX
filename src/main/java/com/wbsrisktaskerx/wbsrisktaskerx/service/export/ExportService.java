package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.ExcelUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService implements IExportService{
    private final CustomerJpaQueryRepository customerJpaQueryRepository;

    public ExportService (CustomerJpaQueryRepository customerJpaQueryRepository){
        this.customerJpaQueryRepository =customerJpaQueryRepository;
    }

    @Override
    public ByteArrayInputStream getCustomerList() throws IOException {
        List<Customer> customers = customerJpaQueryRepository.getAll();
        return ExcelUtils.customerToExcel(customers);
    }
}
