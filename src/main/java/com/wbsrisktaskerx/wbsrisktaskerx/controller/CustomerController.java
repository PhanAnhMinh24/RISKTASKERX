package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.IsActiveCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.ICustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(EndpointConstants.SEARCH_FILTER)
    public ResponseEntity<ApiResult<Page<CustomerResponse>>> searchFilterCustomers(@RequestBody PagingRequest<SearchFilterCustomersRequest> request) {
        Page<CustomerResponse> pageResult = customerService.searchAndFilterCustomers(request);
        return ResponseEntity.ok(ApiResult.success(pageResult));
    }

    @PutMapping(EndpointConstants.UPDATE_CUSTOMER_ACTIVE)
    public ResponseEntity<ApiResult<Boolean>> updateCustomerActive(@RequestBody IsActiveCustomersRequest request) {
        boolean result = customerService.updateCustomerIsActive(request);
        return ResponseEntity.ok(ApiResult.success(result));
    }

}
