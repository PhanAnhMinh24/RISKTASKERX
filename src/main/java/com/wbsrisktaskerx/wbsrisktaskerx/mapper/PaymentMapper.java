package com.wbsrisktaskerx.wbsrisktaskerx.mapper;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Order;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Payment;
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
                .installmentAmount(payment.getInstallmentAmount())
                .installmentPlan(payment.getInstallmentPlan())
                .remainingInstallmentMonths(payment.getRemainingInstallmentMonths())
                .monthlyPayment(payment.getMonthlyPayment())
                .dueDate(payment.getDueDate())
                .paymentDate(payment.getPaymentDate())
                .build();
    }
}
