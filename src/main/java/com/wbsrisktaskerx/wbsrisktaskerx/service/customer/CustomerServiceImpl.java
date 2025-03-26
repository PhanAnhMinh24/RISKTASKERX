package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerJpaQueryRepository customerJpaQueryRepository;

    public CustomerServiceImpl(CustomerJpaQueryRepository customerJpaQueryRepository) {
        this.customerJpaQueryRepository = customerJpaQueryRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerJpaQueryRepository.getAll();
    }

    @Override
    public Page<CustomerResponse> searchAndFilterCustomers(PagingRequest<SearchFilterCustomersRequest> request) {
        return customerJpaQueryRepository.searchedAndFilteredCustomers(request);
    }


}
