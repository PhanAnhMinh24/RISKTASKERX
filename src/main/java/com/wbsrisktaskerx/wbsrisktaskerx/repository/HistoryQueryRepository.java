package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.*;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.HistoryMapper;
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

    public PurchaseHistoryResponse findPurchaseHistoryByPaymentsId(List<PurchaseHistoryResponse> list, Integer paymentsId) {
        return list.stream().filter(p -> p.getPayment().getId().equals(paymentsId))
                .findFirst().orElse(null);
    }

    public Page<PurchaseHistoryResponse> getPurchaseHistory(PagingRequest<HistoryRequest> request) {
        HistoryRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        Integer id = filter.getCustomerId();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(purchaseHistory.customer.id.eq(id));

        List<PurchaseHistoryResponse> purchaseHistoryResponses = getListPurchaseHistoryWithPaging(id, pageable);

        long total = Optional.ofNullable(
                jpaQueryFactory.select(purchaseHistory.count())
                        .from(purchaseHistory)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(purchaseHistoryResponses, pageable, total);
    }

    public List<PurchaseHistoryResponse> getListPurchaseHistoryWithExport(Integer id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(purchaseHistory.customer.id.eq(id));

        QPurchaseHistory purchaseHistory = QPurchaseHistory.purchaseHistory;
        QCar car = QCar.car;
        QCarBrand brand = QCarBrand.carBrand;
        QCarCategory category = QCarCategory.carCategory;
        QAdmin seller = QAdmin.admin;
        QCustomer customer = QCustomer.customer;

        return jpaQueryFactory
                .selectFrom(purchaseHistory)
                .innerJoin(car).on(purchaseHistory.car.id.eq(car.id))
                .innerJoin(brand).on(car.brand.id.eq(brand.id))
                .innerJoin(category).on(car.category.id.eq(category.id))
                .innerJoin(seller).on(purchaseHistory.seller.id.eq(seller.id))
                .innerJoin(customer).on(purchaseHistory.customer.id.eq(customer.id))
                .where(builder)
                .fetch()
                .stream()
                .map(HistoryMapper::purchaseHistoryMapper)
                .collect(Collectors.toList());
    }

    public List<PurchaseHistoryResponse> getListPurchaseHistoryWithPaging(Integer id, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(purchaseHistory.customer.id.eq(id));

        QPurchaseHistory purchaseHistory = QPurchaseHistory.purchaseHistory;
        QCar car = QCar.car;
        QCarBrand brand = QCarBrand.carBrand;
        QCarCategory category = QCarCategory.carCategory;
        QAdmin seller = QAdmin.admin;
        QCustomer customer = QCustomer.customer;

        return jpaQueryFactory
                .selectFrom(purchaseHistory)
                .innerJoin(car).on(purchaseHistory.car.id.eq(car.id))
                .innerJoin(brand).on(car.brand.id.eq(brand.id))
                .innerJoin(category).on(car.category.id.eq(category.id))
                .innerJoin(seller).on(purchaseHistory.seller.id.eq(seller.id))
                .innerJoin(customer).on(purchaseHistory.customer.id.eq(customer.id))
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(HistoryMapper::purchaseHistoryMapper)
                .collect(Collectors.toList());
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(HistoryMapper::warrantyHistoryMapper)
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