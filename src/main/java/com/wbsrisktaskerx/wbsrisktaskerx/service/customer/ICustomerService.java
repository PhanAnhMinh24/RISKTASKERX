package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;

public interface ICustomerService {
    CustomerResponse getCustomerById(int id);
}

