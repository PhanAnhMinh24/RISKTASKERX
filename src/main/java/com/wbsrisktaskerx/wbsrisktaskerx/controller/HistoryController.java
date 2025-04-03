package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.HistoryPagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PurchaseHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.WarrantyHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.HistoryQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(EndpointConstants.HISTORY)
public class HistoryController {

    private final HistoryQueryRepository historyQueryRepository;

    public HistoryController(HistoryQueryRepository historyQueryRepository) {
        this.historyQueryRepository = historyQueryRepository;
    }

    @PostMapping(EndpointConstants.PURCHASE + EndpointConstants.ID)
    public ResponseEntity<Page<PurchaseHistoryResponse>> getPurchaseHistory(@RequestBody PagingRequest<HistoryPagingRequest> request) {
        Page<PurchaseHistoryResponse> purchaseHistoryResponses = historyQueryRepository.getPurchaseHistory(request);
        if(purchaseHistoryResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchaseHistoryResponses);
    }

    @PostMapping(EndpointConstants.WARRANTY + EndpointConstants.ID)
    public ResponseEntity<Page<WarrantyHistoryResponse>> getWarrantyHistory(@RequestBody PagingRequest<HistoryPagingRequest> request) {
        Page<WarrantyHistoryResponse> warrantyHistoryResponses = historyQueryRepository.getWarrantyHistory(request);
        if(warrantyHistoryResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(warrantyHistoryResponses);
    }
}
