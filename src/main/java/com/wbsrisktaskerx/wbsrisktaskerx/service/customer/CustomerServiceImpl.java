package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.CustomerRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerJpaQueryRepository customerJpaQueryRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerJpaQueryRepository customerJpaQueryRepository) {
        this.customerRepository = customerRepository;
        this.customerJpaQueryRepository = customerJpaQueryRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerJpaQueryRepository.getAll();
    }

    @Override
    public CustomerResponse getCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return new CustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getIsActive(),
                customer.getTier(),
                customer.getDateOfBirth()
        );
    }

    @Override
    public Page<CustomerResponse> searchAndFilterCustomers(PagingRequest<SearchFilterCustomersRequest> request) {
        return customerJpaQueryRepository.searchedAndFilteredCustomers(request);
    }

    @Override
    @Transactional
    public boolean updateIsActive(CustomerRequest request) {
        Customer customer = findById(request.getId());
        customer.setIsActive(request.getIsActive());
        customerRepository.save(customer);
        return Boolean.TRUE;
    }

    private Customer findById(Integer id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        return customer.get();
    }

        @Override
        public CustomerResponse getCustomerById(int id) {
            Customer customer = findById(id);
            return new CustomerResponse(
                    customer.getId(),
                    customer.getFullName(),
                    customer.getEmail(),
                    customer.getAddress(),
                    customer.getPhoneNumber(),
                    customer.getIsActive(),
                    customer.getTier(),
                    customer.getDateOfBirth()
            );
        }
}
