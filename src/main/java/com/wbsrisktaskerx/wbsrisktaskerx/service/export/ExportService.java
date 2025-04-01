package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QCustomer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.*;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.ExcelUtils;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PasswordExport;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QPurchaseHistory.purchaseHistory;
import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QWarrantyHistory.warrantyHistory;

@Service
public class ExportService implements IExportService{
    private final CustomerJpaQueryRepository customerJpaQueryRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final QCustomer customer = QCustomer.customer;
    private final CustomerServiceImpl customerService;

    public ExportService (CustomerJpaQueryRepository customerJpaQueryRepository, JPAQueryFactory jpaQueryFactory, CustomerServiceImpl customerService){
        this.customerJpaQueryRepository =customerJpaQueryRepository;
        this.jpaQueryFactory = jpaQueryFactory;
        this.customerService = customerService;
    }

    @Override
    public ExportCustomerResponse getCustomerList(PagingRequest<SearchFilterCustomersRequest> request) throws IOException {

        SearchFilterCustomersRequest filter = request.getFilters();
        String searchKey = filter.getSearchKey();

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotBlank(searchKey)) {
            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.or(customer.fullName.like(CommonConstants.WILDCARD + searchKey + CommonConstants.WILDCARD));
            if (NumberUtils.isCreatable(searchKey)) {
                Integer idValue = Integer.valueOf(searchKey);
                searchBuilder.or(customer.id.eq(idValue));
            }
            builder.and(searchBuilder);
        }

        if (!ObjectUtils.isEmpty(filter.getTier())) {
            builder.and(customer.tier.in(filter.getTier()));
        }
        if (!ObjectUtils.isEmpty(filter.getIsActive())) {
            builder.and(customer.isActive.in(filter.getIsActive()));
        }

        List<CustomerResponse> content;
        content = customerJpaQueryRepository.getAll().stream()
                .map(c -> new CustomerResponse(c.getId(), c.getFullName(), c.getEmail(),
                        c.getAddress(), c.getPhoneNumber(), c.getIsActive(), c.getTier(), c.getDateOfBirth()))
                .collect(Collectors.toList());

        content = jpaQueryFactory.select(
                new QCustomerResponse(
                                customer.id,
                                customer.fullName,
                                customer.email,
                                customer.address,
                                customer.phoneNumber,
                                customer.isActive,
                                customer.tier,
                                customer.dateOfBirth
                        ))
                .from(customer)
                .where(builder)
                .fetch();

        ExportDetails details = generateExportDetails();
        String fileName = String.format(ExportConstants.FILE_FORMAT, ExportConstants.FILENAME, details.currentDate, ExportConstants.XLSX);
        return ExcelUtils.customerToExcel(content, details.password, fileName);
    }

    @Override
    public ExportCustomerResponse exportCustomerPurchaseHistory(Integer customerId) throws IOException {
        CustomerResponse customerResponse = customerService.findOneById(customerId);
        List<PurchaseHistoryResponse> purchaseHistories = jpaQueryFactory.selectFrom(purchaseHistory)
                .where(purchaseHistory.customer.id.eq(customerId))
                .fetch()
                .stream()
                .map(p -> PurchaseHistoryResponse.builder()
                        .id(p.getId())
                        .customer(customerResponse)
                        .carModel(p.getCarModel())
                        .vehicleIdentificationNumber(p.getVehicleIdentificationNumber())
                        .purchaseDate(p.getPurchaseDate())
                        .paymentMethod(p.getPaymentMethod())
                        .price(p.getPrice())
                        .warrantyMonths(p.getWarrantyMonths())
                        .build())
                .collect(Collectors.toList());

        ExportDetails details = generateExportDetails();
        String fileName = String.format(ExportConstants.ID_FILE_FORMAT, ExportConstants.PURCHASE_HISTORY_CUSTOMER, customerId, details.currentDate, ExportConstants.XLSX);
        ExportCustomerResponse response = ExcelUtils.purchaseHistoryToExcel(purchaseHistories, details.password, fileName);

        return response;
    }

    @Override
    public ExportCustomerResponse exportCustomerWarrantyHistory(Integer customerId) throws IOException {
        CustomerResponse customerResponse = customerService.findOneById(customerId);
        List<WarrantyHistoryResponse> warrantyHistoryResponses = jpaQueryFactory.selectFrom(warrantyHistory)
                .where(warrantyHistory.customer.id.eq(customerId))
                .fetch()
                .stream()
                .map(w -> WarrantyHistoryResponse.builder()
                        .id(w.getId())
                        .customer(customerResponse)
                        .carModel(w.getCarModel())
                        .licensePlate(w.getLicensePlate())
                        .serviceType(w.getServiceType())
                        .serviceCenter(w.getServiceCenter())
                        .serviceDate(w.getServiceDate())
                        .serviceCost(w.getServiceCost())
                        .build())
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