package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerDetailsRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerJpaQueryRepository customerJpaQueryRepository;
    private final CustomerDetailsRepository customerDetailsRepository;

    public CustomerServiceImpl(CustomerJpaQueryRepository customerJpaQueryRepository, CustomerDetailsRepository customerDetailsRepository){
        this.customerJpaQueryRepository =customerJpaQueryRepository;
        this.customerDetailsRepository = customerDetailsRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerJpaQueryRepository.getAll();
    }

    public Customer getCustomerById(int id) {
        return customerDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found!"));
    }
}
