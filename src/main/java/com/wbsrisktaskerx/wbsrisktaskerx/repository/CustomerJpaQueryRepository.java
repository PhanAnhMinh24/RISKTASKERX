package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.StringConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.QCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.MaskUtils;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QCustomer.customer;

@Component
public class CustomerJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public CustomerJpaQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<CustomerResponse> getAll() {
        return jpaQueryFactory.select(
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
                .fetch();
    }

    public Page<CustomerResponse> searchedAndFilteredCustomers(PagingRequest<SearchFilterCustomersRequest> request) {
        SearchFilterCustomersRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        String searchKey = filter.getSearchKey();

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotBlank(searchKey)) {
            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.or(customer.fullName.like(
                    String.format(StringConstants.PERCENT, CommonConstants.WILDCARD, searchKey, CommonConstants.WILDCARD)
            ));
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

        List<CustomerResponse> content = jpaQueryFactory.select(
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory.select(customer.id.count())
                        .from(customer)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }

    public List<CustomerResponse> findCustomersByFilter(SearchFilterCustomersRequest filter) {
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

        return jpaQueryFactory.select(
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
    }
}
