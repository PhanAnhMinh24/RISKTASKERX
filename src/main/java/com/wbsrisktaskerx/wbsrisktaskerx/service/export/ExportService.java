package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.*;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.HistoryMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.*;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.CarMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.ExcelUtils;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PasswordExport;
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

    public ExportService (CustomerJpaQueryRepository customerJpaQueryRepository, JPAQueryFactory jpaQueryFactory, CustomerServiceImpl customerService){
        this.customerJpaQueryRepository =customerJpaQueryRepository;
        this.jpaQueryFactory = jpaQueryFactory;
        this.customerService = customerService;
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
    public ExportCustomerResponse exportCustomerPurchaseHistory(Integer customerId) throws IOException {
        CustomerResponse customerResponse = customerService.findOneById(customerId);
        QPurchaseHistory purchaseHistory = QPurchaseHistory.purchaseHistory;
        QCar car = QCar.car;
        QCarBrand brand = QCarBrand.carBrand;
        QCarCategory category = QCarCategory.carCategory;
        QAdmin seller = QAdmin.admin;
        QCustomer customer = QCustomer.customer;

        List<PurchaseHistoryResponse> purchaseHistoryResponses = jpaQueryFactory
                .selectFrom(purchaseHistory)
                .innerJoin(purchaseHistory.car, car).fetchJoin()
                .innerJoin(car.brand, brand).fetchJoin()
                .innerJoin(car.category, category).fetchJoin()
                .innerJoin(car.seller, seller).fetchJoin()
                .innerJoin(purchaseHistory.customer, customer).fetchJoin()
                .where(purchaseHistory.customer.id.eq(customerId))
                .fetch()
                .stream()
                .map(HistoryMapper::purchaseHistoryMapper)
                .collect(Collectors.toList());

        ExportDetails details = generateExportDetails();
        String fileName = String.format(ExportConstants.ID_FILE_FORMAT, ExportConstants.PURCHASE_HISTORY_CUSTOMER, customerId, details.currentDate, ExportConstants.XLSX);
        ExportCustomerResponse response = ExcelUtils.purchaseHistoryToExcel(purchaseHistoryResponses, details.password, fileName);

        return response;
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
        ExportCustomerResponse response = ExcelUtils.warrantyHistoryToExcel(warrantyHistoryResponses, details.password, fileName);

        return response;
    }

    private ExportDetails generateExportDetails() {
        String password = PasswordExport.generatePassword();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(ExportConstants.DATE_TIME));
        return new ExportDetails(password, currentDate);
    }
}