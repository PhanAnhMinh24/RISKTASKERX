package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.HistoryRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.*;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QPurchaseHistory.purchaseHistory;
import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QWarrantyHistory.warrantyHistory;

@Component
public class HistoryQueryRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    private final CustomerServiceImpl customerService;

    public HistoryQueryRepository(JPAQueryFactory jpaQueryFactory, CustomerServiceImpl customerService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.customerService = customerService;
    }

    public Page<PurchaseHistoryResponse> getPurchaseHistory(PagingRequest<HistoryRequest> request) {
        HistoryRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        Integer id = filter.getCustomerId();
        Customer customer = customerService.findCustomerById(id);

        BooleanBuilder builder = new BooleanBuilder();
        if (ObjectUtils.isNotEmpty(customer)) {
            builder.and(purchaseHistory.customer.id.eq(id));
        }

        List<PurchaseHistoryResponse> content = jpaQueryFactory
                .select(new QPurchaseHistoryResponse(
                        purchaseHistory.id,
                        purchaseHistory.customer,
                        purchaseHistory.vehicleIdentificationNumber,
                        purchaseHistory.carModel,
                        purchaseHistory.purchaseDate,
                        purchaseHistory.paymentMethod,
                        purchaseHistory.price,
                        purchaseHistory.warrantyMonths
                        ))
                .from(purchaseHistory)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory.select(purchaseHistory.count())
                        .from(purchaseHistory)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }


    public Page<WarrantyHistoryResponse> getWarrantyHistory(PagingRequest<HistoryRequest> request) {
        HistoryRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        Integer id = filter.getCustomerId();
        CustomerResponse customer = customerService.findOneById(id);

        BooleanBuilder builder = new BooleanBuilder();
        if (ObjectUtils.isNotEmpty(customer)) {
            builder.and(warrantyHistory.customer.id.eq(id));
        }

        List<WarrantyHistoryResponse> content = jpaQueryFactory
                .select(new QWarrantyHistoryResponse(
                                warrantyHistory.id,
                                warrantyHistory.customer,
                                warrantyHistory.carModel,
                                warrantyHistory.licensePlate,
                                warrantyHistory.serviceType,
                                warrantyHistory.serviceCenter,
                                warrantyHistory.serviceDate,
                                warrantyHistory.serviceCost
                        ))
                .from(warrantyHistory)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory.select(warrantyHistory.count())
                        .from(warrantyHistory)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
