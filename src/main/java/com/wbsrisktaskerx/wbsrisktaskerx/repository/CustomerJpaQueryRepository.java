package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QCustomer.customer;

@Component
public class CustomerJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public CustomerJpaQueryRepository(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Customer> getAll() {
        return jpaQueryFactory.selectFrom(customer).fetch();
    }
}
