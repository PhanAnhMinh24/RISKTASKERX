package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.PurchaseHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.WarrantyHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.HistoryPagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PurchaseHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.WarrantyHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.customer.CustomerServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointConstants.HISTORY)
public class HistoryController {

    private final CustomerServiceImpl customerService;

    public HistoryController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping(EndpointConstants.PURCHASE + EndpointConstants.ID)
    public ResponseEntity<Page<PurchaseHistoryResponse>> getPurchaseHistory(@RequestBody PagingRequest<HistoryPagingRequest> request, @PathVariable int id) {
        Page<PurchaseHistoryResponse> purchaseHistoryResponses = customerService.getPurchaseHistoryById(request, id);
        if(purchaseHistoryResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchaseHistoryResponses);
    }

    @PostMapping(EndpointConstants.WARRANTY + EndpointConstants.ID)
    public ResponseEntity<Page<WarrantyHistoryResponse>> getWarrantyHistory(@RequestBody PagingRequest<HistoryPagingRequest> request, @PathVariable int id) {
        Page<WarrantyHistoryResponse> warrantyHistoryResponses = customerService.getWarrantyHistoryById(request, id);
        if(warrantyHistoryResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(warrantyHistoryResponses);
    }
}
