package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.ApiResult;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.CustomerRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.WarrantyHistoryRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.ICustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstants.CUSTOMERS)
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping(EndpointConstants.SEARCH_FILTER)
    public ResponseEntity<ApiResult<Page<CustomerResponse>>> searchFilterCustomers(@RequestBody PagingRequest<SearchFilterCustomersRequest> request) {
        Page<CustomerResponse> pageResult = customerService.searchAndFilterCustomers(request);
        return ResponseEntity.ok(ApiResult.success(pageResult));
    }

    @PutMapping(EndpointConstants.STATUS)
    public ResponseEntity<ApiResult<Boolean>> updateCustomerActive(@RequestBody CustomerRequest request) {
        boolean result = customerService.updateIsActive(request);
        return ResponseEntity.ok(ApiResult.success(result));
    }

    @GetMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<CustomerResponse>> getCustomerDetail(@PathVariable int id) {
        return ResponseEntity.ok(ApiResult.success(customerService.getCustomerById(id)));
    }

    @PostMapping(EndpointConstants.WARRANTY + EndpointConstants.ID)
    public ResponseEntity<ApiResult<Boolean>> addWarranty(@RequestBody WarrantyHistoryRequest warrantyHistoryRequest) {
        customerService.addWarrantyHistory(warrantyHistoryRequest);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }

}