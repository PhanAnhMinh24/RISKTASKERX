package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
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
        int currentPage = (request.getPage() != null && request.getPage() > 0) ? request.getPage() : 1;
        int pageSize = (request.getSize() != null && request.getSize() > 0) ? request.getSize() : 10;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        SearchFilterCustomersRequest filter = request.getFilters();

        String searchKey = filter.getSearchKey();
        if (StringUtils.isNotBlank(searchKey)) {
            try {
                Integer idValue = Integer.valueOf(searchKey);
                booleanBuilder.and(customer.id.eq(idValue).or(customer.fullName.containsIgnoreCase(searchKey)));
            } catch (NumberFormatException e) {
                booleanBuilder.and(customer.fullName.containsIgnoreCase(searchKey));
            }
        }

        if (!ObjectUtils.isEmpty(filter.getTier())) {
            booleanBuilder.and(customer.tier.eq(filter.getTier()));
        }
        if (!ObjectUtils.isEmpty(filter.getIsActive())) {
            booleanBuilder.and(customer.isActive.eq(filter.getIsActive()));
        }

        long total = Optional.ofNullable(
                jpaQueryFactory.select(customer.id.count()).from(customer).where(booleanBuilder).fetchOne()
        ).orElse(0L);

        if (total == 0) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(currentPage - 1, pageSize), total);
        }

        JPAQuery<CustomerResponse> query = jpaQueryFactory.select(
                        new QCustomerResponse(
                                customer.id,
                                customer.fullName,
                                customer.email,
                                customer.phoneNumber,
                                customer.isActive,
                                customer.tier
                        ))
                .from(customer)
                .where(booleanBuilder)
                .offset((currentPage - 1) * pageSize)
                .limit(pageSize);

        List<CustomerResponse> content = query.fetch();

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return new PageImpl<>(content, pageable, total);
    }


}
