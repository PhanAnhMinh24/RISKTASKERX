package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentMethods;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallmentsResponse {
    Integer id;
    PaymentResponse payment;
    PaymentMethods paymentMethods;
    Float installmentAmount;
    Integer installmentPlan;
    Integer remainingInstallmentMonths;
    Float monthlyPayment;
    OffsetDateTime dueDate;
    OffsetDateTime paymentDate;

    @QueryProjection
    public InstallmentsResponse(Integer id, PaymentResponse payment, PaymentMethods paymentMethods, Float installmentAmount,
                                Integer installmentPlan, Integer remainingInstallmentMonths, Float monthlyPayment,
                                OffsetDateTime dueDate, OffsetDateTime paymentDate) {
        this.id = id;
        this.payment = payment;
        this.paymentMethods = paymentMethods;
        this.installmentAmount = installmentAmount;
        this.installmentPlan = installmentPlan;
        this.remainingInstallmentMonths = remainingInstallmentMonths;
        this.monthlyPayment = monthlyPayment;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
    }
}
