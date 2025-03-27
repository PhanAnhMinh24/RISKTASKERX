package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.PurchaseHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointConstants.CUSTOMERS)
public class HistoryController {

    private final CustomerServiceImpl customerService;

    public HistoryController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping(EndpointConstants.ID + EndpointConstants.HISTORY)
    public ResponseEntity<List<PurchaseHistory>> getPurchaseHistory(@PathVariable int id) {
        List<PurchaseHistory> purchaseHistoryList = customerService.getPurchaseHistoryById(id);
        if(purchaseHistoryList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchaseHistoryList);
    }
}
