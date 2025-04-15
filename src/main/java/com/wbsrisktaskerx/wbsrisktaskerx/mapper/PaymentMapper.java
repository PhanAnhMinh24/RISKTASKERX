package com.wbsrisktaskerx.wbsrisktaskerx.mapper;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Installments;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Order;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Payment;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.InstallmentsResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.OrderResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PaymentResponse;

import static com.wbsrisktaskerx.wbsrisktaskerx.mapper.AdminMapper.adminMapper;
import static com.wbsrisktaskerx.wbsrisktaskerx.mapper.CustomerMapper.customerMapper;

public class PaymentMapper {
    public static OrderResponse orderMapper(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .admin(adminMapper(order.getAdmin()))
                .customer(customerMapper(order.getCustomer()))
                .status(order.getStatus())
                .build();
    }

    public static PaymentResponse paymentMapper(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .order(orderMapper(payment.getOrder()))
                .paymentMethod(payment.getPaymentMethod())
                .paymentOption(payment.getPaymentOption())
                .invoice(payment.getInvoice())
                .price(payment.getPrice())
                .initialPayment(payment.getInitialPayment())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

    public static InstallmentsResponse installmentsMapper(Installments installments) {
        return InstallmentsResponse.builder()
                .id(installments.getId())
                .payment(paymentMapper(installments.getPayments()))
                .paymentMethods(installments.getPaymentMethod())
                .installmentAmount(installments.getInstallmentAmount())
                .installmentPlan(installments.getInstallmentPlan())
                .remainingInstallmentMonths(installments.getRemainingInstallmentMonths())
                .monthlyPayment(installments.getMonthlyPayment())
                .dueDate(installments.getDueDate())
                .paymentDate(installments.getPaymentDate())
                .build();
    }
}
