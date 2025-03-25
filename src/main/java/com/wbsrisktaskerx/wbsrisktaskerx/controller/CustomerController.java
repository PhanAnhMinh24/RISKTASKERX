package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.FilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.ICustomerService;
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

    @GetMapping(EndpointConstants.FILTER)
    public ResponseEntity<ApiResult<List<Customer>>> filterCustomers(@ModelAttribute FilterCustomersRequest filterRequest) {
        List<Customer> customers = customerService.filterCustomers(
                filterRequest.getTier(),
                filterRequest.getIsActive(),
                filterRequest.getPage(),
                filterRequest.getSize());
        return ResponseEntity.ok(ApiResult.success(customers));
    }
    @GetMapping(EndpointConstants.SEARCH)
    public ResponseEntity<ApiResult<List<Customer>>> searchCustomers(@ModelAttribute SearchCustomersRequest searchRequest) {
        List<Customer> customers = customerService.searchCustomers(
                searchRequest.getId(),
                searchRequest.getFullName(),
                searchRequest.getPage(),
                searchRequest.getSize());
        return ResponseEntity.ok(ApiResult.success(customers));
    }
}
