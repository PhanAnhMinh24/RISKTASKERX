package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.*;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.HistoryRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.*;
import com.wbsrisktaskerx.wbsrisktaskerx.service.car.CarServiceBuilder;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(purchaseHistory.customer.id.eq(id));

        QPurchaseHistory purchaseHistory = QPurchaseHistory.purchaseHistory;
        QCar car = QCar.car;
        QCarBrand brand = QCarBrand.carBrand;
        QCarCategory category = QCarCategory.carCategory;
        QAdmin seller = QAdmin.admin;
        QCustomer customer = QCustomer.customer;

        List<PurchaseHistoryResponse> purchaseHistoryResponses = jpaQueryFactory
                .selectFrom(purchaseHistory)
                .join(purchaseHistory.car, car).fetchJoin()
                .join(car.brand, brand).fetchJoin()
                .join(car.category, category).fetchJoin()
                .join(car.seller, seller).fetchJoin()
                .join(purchaseHistory.customer, customer).fetchJoin()
                .where(builder)
                .fetch()
                .stream()
                .map(CarServiceBuilder::purchaseHistoryBuilder)
                .collect(Collectors.toList());

        long total = Optional.ofNullable(
                jpaQueryFactory.select(purchaseHistory.count())
                        .from(purchaseHistory)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(purchaseHistoryResponses, pageable, total);
    }


    public Page<WarrantyHistoryResponse> getWarrantyHistory(PagingRequest<HistoryRequest> request) {
        HistoryRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        Integer id = filter.getCustomerId();
        CustomerResponse customerResponse = customerService.findOneById(id);

        BooleanBuilder builder = new BooleanBuilder();
        if (ObjectUtils.isNotEmpty(customerResponse)) {
            builder.and(warrantyHistory.customer.id.eq(id));
        }

        List<WarrantyHistoryResponse> warrantyHistoryResponses = jpaQueryFactory
                .selectFrom(warrantyHistory)
                .where(builder)
                .fetch()
                .stream()
                .map(CarServiceBuilder::warrantyHistoryBuilder)
                .collect(Collectors.toList());

        long total = Optional.ofNullable(
                jpaQueryFactory.select(warrantyHistory.count())
                        .from(warrantyHistory)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(warrantyHistoryResponses, pageable, total);
    }
}