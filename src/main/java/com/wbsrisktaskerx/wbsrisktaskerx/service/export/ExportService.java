package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QCustomer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.QCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
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

@Service
public class ExportService implements IExportService{
    private final CustomerJpaQueryRepository customerJpaQueryRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final QCustomer customer = QCustomer.customer;

    public ExportService (CustomerJpaQueryRepository customerJpaQueryRepository, JPAQueryFactory jpaQueryFactory){
        this.customerJpaQueryRepository =customerJpaQueryRepository;
        this.jpaQueryFactory = jpaQueryFactory;
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
            builder.and(customer.tier.eq(filter.getTier()));
        }
        if (!ObjectUtils.isEmpty(filter.getIsActive())) {
            builder.and(customer.isActive.eq(filter.getIsActive()));
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

        String password = PasswordExport.generatePassword();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(ExportConstants.DATE_TIME));
        String fileName = ExportConstants.FILENAME + currentDate + ExportConstants.XLSX;

        return ExcelUtils.customerToExcel(content, password, fileName);
    }
}