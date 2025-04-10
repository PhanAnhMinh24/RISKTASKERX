package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentMethods;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentOptions;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethods paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_option", nullable = false)
    private PaymentOptions paymentOption;

    @Column(nullable = false)
    private String invoice;

    @Column(nullable = false)
    private Float price;

    @Column(name = "initial_payment")
    private Float initialPayment;

    @Column(name = "installment_amount")
    private Float installmentAmount;

    @Column(name = "installment_plan")
    private Integer installmentPlan;

    @Column(name = "remaining_installment_months")
    private Integer remainingInstallmentMonths;

    @Column(name = "monthly_payment")
    private Float monthlyPayment;

    @Column(name = "due_date")
    private OffsetDateTime dueDate;

    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;
}
