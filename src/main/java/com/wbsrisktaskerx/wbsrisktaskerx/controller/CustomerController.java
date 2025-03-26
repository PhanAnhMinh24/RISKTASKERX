package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.ICustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointConstants.CUSTOMERS)
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(EndpointConstants.LIST_CUSTOMERS)
    public ResponseEntity<ApiResult<List<Customer>>> getAllCustomers() {
        return ResponseEntity.ok(ApiResult.success(customerService.getAllCustomers()));
    }

    @GetMapping(EndpointConstants.SEARCH_FILTER)
    public ResponseEntity<ApiResult<List<Customer>>> searchFilterCustomers(@ModelAttribute SearchFilterCustomersRequest request) {
        Page<Customer> pageResult = customerService.searchAndFilterCustomers(request);
        List<Customer> customers = pageResult.getContent();
        return ResponseEntity.ok(ApiResult.success(customers));
    }


}
