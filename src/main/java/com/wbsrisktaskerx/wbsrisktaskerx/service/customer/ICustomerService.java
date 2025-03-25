package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();
    List<Customer> filterCustomers(Tier tier, Boolean isActive, Integer page, Integer size);
    List<Customer> searchCustomers(Integer id, String fullName, Integer page, Integer size);
}
