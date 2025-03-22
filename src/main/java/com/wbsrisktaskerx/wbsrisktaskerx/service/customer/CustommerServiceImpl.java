package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustommerServiceImpl implements ICustomerService {

    private final CustomerJpaQueryRepository customerJpaQueryRepository;

    public CustommerServiceImpl (CustomerJpaQueryRepository customerJpaQueryRepository){
        this.customerJpaQueryRepository =customerJpaQueryRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerJpaQueryRepository.getAll();
    }
}
