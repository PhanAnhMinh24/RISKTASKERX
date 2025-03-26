package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;


import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService {

    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    public Customer getCustomerById(int id) {
        return customerDetailRepository.findById(id)
                .orElse(null);
    }
}
