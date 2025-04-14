package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.HistoryRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.InstallmentsResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PurchaseHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.WarrantyHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.HistoryQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.service.installments.InstallmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(EndpointConstants.HISTORY)
public class HistoryController {

    private final HistoryQueryRepository historyQueryRepository;
    private final InstallmentService installmentService;

    public HistoryController(HistoryQueryRepository historyQueryRepository, InstallmentService installmentService) {
        this.historyQueryRepository = historyQueryRepository;
        this.installmentService = installmentService;
    }

    @PostMapping(EndpointConstants.PURCHASE)
    public ResponseEntity<Page<PurchaseHistoryResponse>> getPurchaseHistory(@RequestBody PagingRequest<HistoryRequest> request) {
        Page<PurchaseHistoryResponse> purchaseHistoryResponses = historyQueryRepository.getPurchaseHistory(request);
        return ResponseEntity.ok(purchaseHistoryResponses);
    }

    @PostMapping(EndpointConstants.WARRANTY)
    public ResponseEntity<Page<WarrantyHistoryResponse>> getWarrantyHistory(@RequestBody PagingRequest<HistoryRequest> request) {
        Page<WarrantyHistoryResponse> warrantyHistoryResponses = historyQueryRepository.getWarrantyHistory(request);
        return ResponseEntity.ok(warrantyHistoryResponses);
    }

    @GetMapping(EndpointConstants.PURCHASE + EndpointConstants.PAYMENTS_ID)
    public ResponseEntity<List<InstallmentsResponse>> getInstallmentsByPaymentId(@PathVariable Integer paymentsId) {
        List<InstallmentsResponse> installments = installmentService.getListInstallments(paymentsId);
        return ResponseEntity.ok(installments);
    }
}
