package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.QCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QCustomer.customer;

@Component
public class CustomerJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public CustomerJpaQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Customer> getAll() {
        return jpaQueryFactory.selectFrom(customer).fetch();
    }


    public Page<CustomerResponse> searchedAndFilteredCustomers(PagingRequest<SearchFilterCustomersRequest> request) {
        BooleanBuilder builder = new BooleanBuilder();
        SearchFilterCustomersRequest filter = request.getFilters();
        String searchKey = filter.getSearchKey();
        if (StringUtils.isNotBlank(searchKey)) {
            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.or(customer.fullName.like("%" + searchKey + "%"));
            try {
                Integer idValue = Integer.valueOf(searchKey);
                searchBuilder.or(customer.id.eq(idValue));
            } catch (NumberFormatException e) {
            }
            builder.and(searchBuilder);
        }
        if (!ObjectUtils.isEmpty(filter.getTier())) {
            builder.and(customer.tier.eq(filter.getTier()));
        }
        if (!ObjectUtils.isEmpty(filter.getIsActive())) {
            builder.and(customer.isActive.eq(filter.getIsActive()));
        }
        long total = Optional.ofNullable(
                jpaQueryFactory.select(customer.id.count())
                        .from(customer)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        Pageable pageable = PageService.getPageRequest(request);
        if (total == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, total);
        }

        List<CustomerResponse> content = jpaQueryFactory.select(
                        new QCustomerResponse(
                                customer.id,
                                customer.fullName,
                                customer.email,
                                customer.phoneNumber,
                                customer.isActive,
                                customer.tier
                        ))
                .from(customer)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }


}
