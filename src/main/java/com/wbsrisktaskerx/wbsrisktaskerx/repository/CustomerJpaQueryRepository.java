package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public Page<CustomerResponse> searchedAndFilteredCustomers(SearchFilterCustomersRequest request) {
        int currentPage = (request.getPage() != null && request.getPage() > 0) ? request.getPage() : 1;
        int pageSize = (request.getSize() != null && request.getSize() > 0) ? request.getSize() : 10;

        JPAQuery<Customer> query = jpaQueryFactory.selectFrom(customer);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        String searchKey = request.getSearchKey();
        if (StringUtils.isNotBlank(searchKey)) {
            try {
                Integer idValue = Integer.valueOf(searchKey);
                booleanBuilder.and(customer.id.eq(idValue)
                        .or(customer.fullName.containsIgnoreCase(searchKey)));
            } catch (NumberFormatException e) {
                booleanBuilder.and(customer.fullName.containsIgnoreCase(searchKey));
            }
        }

        if (!ObjectUtils.isEmpty(request.getTier())) {
            booleanBuilder.and(customer.tier.eq(request.getTier()));
        }
        if (!ObjectUtils.isEmpty(request.getIsActive())) {
            booleanBuilder.and(customer.isActive.eq(request.getIsActive()));
        }

        query.where(booleanBuilder);

        long total = query.fetchCount();

        query = PageService.applyPagination(query, currentPage, pageSize, null, null);

        List<CustomerResponse> content = query.fetch().stream()
                .map(cust -> new CustomerResponse(
                        cust.getId(),
                        cust.getFullName(),
                        cust.getEmail(),
                        cust.getPhoneNumber(),
                        cust.getIsActive(),
                        cust.getTier()
                ))
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return new PageImpl<>(content, pageable, total);
    }

}
