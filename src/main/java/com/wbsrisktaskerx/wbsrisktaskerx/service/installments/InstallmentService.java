package com.wbsrisktaskerx.wbsrisktaskerx.service.installments;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Installments;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.PaymentMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.InstallmentsResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.InstallmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstallmentService{
    private final InstallmentRepository installmentRepository;

    public InstallmentService(InstallmentRepository iInstallmentService) {
        this.installmentRepository = iInstallmentService;
    }

    public List<InstallmentsResponse> getListInstallments(Integer paymentsId) {
        List<Installments> installmentsList = installmentRepository.findByPaymentsId(paymentsId);
        return installmentsList.stream().map(PaymentMapper::installmentsMapper)
                .collect(Collectors.toList());
    }
}
