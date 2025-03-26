package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
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


    public Page<Customer> getSearchedAndFilteredCustomers(
            Integer id, String fullName, Tier tier, Boolean isActive, Integer page, Integer size) {
        int currentPage = (page != null && page > 0) ? page : 1;
        int pageSize = (size != null && size > 0) ? size : 50;
        JPAQuery<Customer> query = jpaQueryFactory.selectFrom(customer);
        List<BooleanExpression> conditions = new ArrayList<>();

        if (!ObjectUtils.isEmpty(id)) {
            conditions.add(customer.id.eq(id));
        }
        if (StringUtils.isNotBlank(fullName)) {
            conditions.add(customer.fullName.containsIgnoreCase(fullName));
        }
        if (!ObjectUtils.isEmpty(tier)) {
            conditions.add(customer.tier.eq(tier));
        }
        if (!ObjectUtils.isEmpty(isActive)) {
            conditions.add(customer.isActive.eq(isActive));
        }

        if (!conditions.isEmpty()) {
            BooleanExpression combinedCondition = conditions.stream()
                    .reduce(BooleanExpression::and)
                    .orElse(null);
            query.where(combinedCondition);
        }

        long total = query.fetchCount();
        query = PageService.applyPagination(query, currentPage, pageSize, null, null);
        List<Customer> content = query.fetch();
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        return new PageImpl<>(content, pageable, total);
    }

}
