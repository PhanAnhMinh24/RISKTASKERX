package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();
    Page<Customer> searchAndFilterCustomers(SearchFilterCustomersRequest request);
}
