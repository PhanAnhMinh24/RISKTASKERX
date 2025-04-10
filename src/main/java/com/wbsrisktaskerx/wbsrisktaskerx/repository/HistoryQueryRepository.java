package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    public Page<PurchaseHistoryResponse> getPurchaseHistory(PagingRequest<HistoryRequest> request) {
        HistoryRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        Integer id = filter.getCustomerId();
        CustomerResponse customerResponse = customerService.findOneById(id);

        BooleanBuilder builder = new BooleanBuilder();
        if (ObjectUtils.isNotEmpty(customerResponse)) {
            builder.and(purchaseHistory.customer.id.eq(id));
        }

        List<PurchaseHistoryResponse> purchaseHistoryResponses = jpaQueryFactory
                .selectFrom(purchaseHistory)
                .where(builder)
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

        long total = Optional.ofNullable(
                jpaQueryFactory.select(warrantyHistory.count())
                        .from(warrantyHistory)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(warrantyHistoryResponses, pageable, total);
    }
}
