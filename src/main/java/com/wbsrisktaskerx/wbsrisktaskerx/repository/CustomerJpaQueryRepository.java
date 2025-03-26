package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import org.springframework.stereotype.Component;

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

    public List<Customer> getSearchedAndFilteredCustomers(Integer id, String fullName, Tier tier, Boolean isActive, Integer page, Integer size) {
        JPAQuery<Customer> query = jpaQueryFactory.selectFrom(customer);
        BooleanExpression condition = null;

        if (id != null) {
            condition = customer.id.eq(id);
        }
        if (fullName != null && !fullName.isEmpty()) {
            BooleanExpression fullNameCondition = customer.fullName.containsIgnoreCase(fullName);
            // Nếu có cả id và fullName thì có thể chọn cách OR hoặc AND tùy vào yêu cầu nghiệp vụ
            condition = (condition != null) ? condition.or(fullNameCondition) : fullNameCondition;
        }
        if (tier != null) {
            condition = (condition != null) ? condition.and(customer.tier.eq(tier)) : customer.tier.eq(tier);
        }
        if (isActive != null) {
            condition = (condition != null) ? condition.and(customer.isActive.eq(isActive)) : customer.isActive.eq(isActive);
        }
        if (condition != null) {
            query.where(condition);
        }
        query = PageService.applyPagination(query, page, size, null, null);
        return query.fetch();
    }
}
