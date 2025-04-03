package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.HistoryPagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PurchaseHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.WarrantyHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import org.apache.commons.lang3.StringUtils;
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

    public Page<PurchaseHistoryResponse> getPurchaseHistory(PagingRequest<HistoryPagingRequest> request) {
        HistoryPagingRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        Integer id = filter.getCustomerId();
        Customer customer = customerService.findById(id);

        BooleanBuilder builder = new BooleanBuilder();
        if (customer != null) {
            builder.and(purchaseHistory.customer.id.eq(id));
        }

        List<PurchaseHistoryResponse> content = jpaQueryFactory
                .select(Projections.constructor(PurchaseHistoryResponse.class,
                        purchaseHistory.id,
                        Projections.constructor(CustomerResponse.class,
                                purchaseHistory.customer.id,
                                purchaseHistory.customer.fullName,
                                purchaseHistory.customer.email,
                                purchaseHistory.customer.address,
                                purchaseHistory.customer.phoneNumber,
                                purchaseHistory.customer.isActive,
                                purchaseHistory.customer.tier,
                                purchaseHistory.customer.dateOfBirth
                        ),
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


    public Page<WarrantyHistoryResponse> getWarrantyHistory(PagingRequest<HistoryPagingRequest> request) {
        HistoryPagingRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        Integer id = filter.getCustomerId();
        Customer customer = customerService.findById(id);

        BooleanBuilder builder = new BooleanBuilder();
        if (customer != null) {
            builder.and(warrantyHistory.customer.id.eq(id));
        }

        List<WarrantyHistoryResponse> content = jpaQueryFactory
                .select(Projections.constructor(WarrantyHistoryResponse.class,
                        warrantyHistory.id,
                        Projections.constructor(CustomerResponse.class,
                                warrantyHistory.customer.id,
                                warrantyHistory.customer.fullName,
                                warrantyHistory.customer.email,
                                warrantyHistory.customer.address,
                                warrantyHistory.customer.phoneNumber,
                                warrantyHistory.customer.isActive,
                                warrantyHistory.customer.tier,
                                warrantyHistory.customer.dateOfBirth
                        ),
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
