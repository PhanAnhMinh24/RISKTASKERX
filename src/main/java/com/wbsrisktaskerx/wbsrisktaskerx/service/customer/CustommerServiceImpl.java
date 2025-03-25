package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustommerServiceImpl implements ICustomerService {

    private final CustomerJpaQueryRepository customerJpaQueryRepository;
    public CustommerServiceImpl(CustomerJpaQueryRepository customerJpaQueryRepository) {
        this.customerJpaQueryRepository = customerJpaQueryRepository;
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerJpaQueryRepository.getAll();
    }
    @Override
    public List<Customer> filterCustomers(Tier tier, Boolean isActive, Integer page, Integer size) {
        return customerJpaQueryRepository.getFilteredCustomers(tier, isActive, page, size);
    }
    @Override
    public List<Customer> searchCustomers(Integer id, String fullName, Integer page, Integer size) {
        return customerJpaQueryRepository.getSearchedCustomers(id, fullName, page, size);
    }
}
