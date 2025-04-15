package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentMethods;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "installments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Installments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "payments_id")
    Payment payments;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    PaymentMethods paymentMethod;

    @Column(name = "installment_amount")
    Float installmentAmount;

    @Column(name = "installment_plan")
    Integer installmentPlan;

    @Column(name = "remaining_installment_months")
    Integer remainingInstallmentMonths;

    @Column(name = "monthly_payment")
    Float monthlyPayment;

    @Column(name = "due_date")
    OffsetDateTime dueDate;

    @Column(name = "payment_date")
    OffsetDateTime paymentDate;
}
