package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstants.DETAIL_CUSTOMER)
@CrossOrigin(origins = "*")
public class CustomerDetailController {

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerDetail(@PathVariable int id) {
        Customer customer = customerDetailsService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
}
