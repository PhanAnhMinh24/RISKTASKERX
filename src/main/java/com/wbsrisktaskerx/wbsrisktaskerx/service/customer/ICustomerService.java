package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.CustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();
    Page<CustomerResponse> searchAndFilterCustomers(PagingRequest<SearchFilterCustomersRequest> request);
    boolean updateIsActive(CustomersRequest request);
}
