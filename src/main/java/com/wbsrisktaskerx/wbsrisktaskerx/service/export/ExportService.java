package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.HistoryMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ExportHistoryRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.*;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.HistoryQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.InstallmentQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.ExcelUtils;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PasswordExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QWarrantyHistory.warrantyHistory;

@Service
public class ExportService implements IExportService{
    private final CustomerJpaQueryRepository customerJpaQueryRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final CustomerServiceImpl customerService;
    private final HistoryQueryRepository historyQueryRepository;
    private final InstallmentQueryRepository installmentQueryRepository;

    @Autowired
    private ExcelUtils excelUtils;

    public ExportService (CustomerJpaQueryRepository customerJpaQueryRepository, JPAQueryFactory jpaQueryFactory,
                          CustomerServiceImpl customerService, HistoryQueryRepository historyQueryRepository,
                          InstallmentQueryRepository installmentQueryRepository){
        this.customerJpaQueryRepository =customerJpaQueryRepository;
        this.jpaQueryFactory = jpaQueryFactory;
        this.customerService = customerService;
        this.historyQueryRepository = historyQueryRepository;
        this.installmentQueryRepository = installmentQueryRepository;
    }

    @Override
    public ExportCustomerResponse getCustomerList(PagingRequest<SearchFilterCustomersRequest> request) throws IOException {
        SearchFilterCustomersRequest filter = request.getFilters();
        List<CustomerResponse> content = customerJpaQueryRepository.findCustomersByFilter(filter);
        ExportDetails details = generateExportDetails();
        String fileName = String.format(ExportConstants.FILE_FORMAT, ExportConstants.FILENAME, details.currentDate, ExportConstants.XLSX);
        return ExcelUtils.customerToExcel(content, details.password, fileName);
    }

    @Override
    public ExportCustomerResponse exportCustomerPurchaseHistory(ExportHistoryRequest request) throws IOException {
        Integer customerId = request.getId();
        Integer paymentsId = request.getPaymentsId();
        List<PurchaseHistoryResponse> purchaseHistoryResponses = historyQueryRepository.getListPurchaseHistory(customerId);

        List<InstallmentsResponse> installmentsResponses = installmentQueryRepository.getListInstallments(paymentsId);
        ExportDetails details = generateExportDetails();
        String fileName = String.format(ExportConstants.ID_FILE_FORMAT, ExportConstants.PURCHASE_HISTORY_CUSTOMER, customerId, details.currentDate, ExportConstants.XLSX);

        return excelUtils.purchaseHistoryToExcel(purchaseHistoryResponses, installmentsResponses, paymentsId, details.password, fileName);
    }

    @Override
    public ExportCustomerResponse exportCustomerWarrantyHistory(Integer customerId) throws IOException {
        CustomerResponse customerResponse = customerService.findOneById(customerId);
        List<WarrantyHistoryResponse> warrantyHistoryResponses = jpaQueryFactory.selectFrom(warrantyHistory)
                .where(warrantyHistory.customer.id.eq(customerId))
                .fetch()
                .stream()
                .map(HistoryMapper::warrantyHistoryMapper)
                .collect(Collectors.toList());

        ExportDetails details = generateExportDetails();
        String fileName = String.format(ExportConstants.ID_FILE_FORMAT, ExportConstants.WARRANTY_HISTORY_CUSTOMER, customerId, details.currentDate, ExportConstants.XLSX);

        return ExcelUtils.warrantyHistoryToExcel(warrantyHistoryResponses, details.password, fileName);
    }

    private ExportDetails generateExportDetails() {
        String password = PasswordExport.generatePassword();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(ExportConstants.DATE_TIME));
        return new ExportDetails(password, currentDate);
    }
}