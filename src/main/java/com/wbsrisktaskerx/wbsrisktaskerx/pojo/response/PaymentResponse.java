package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentMethods;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentOptions;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    Integer id;
    OrderResponse order;
    PaymentMethods paymentMethod;
    PaymentOptions paymentOption;
    String invoice;
    Float price;
    Float initialPayment;
    OffsetDateTime paymentDate;

    @QueryProjection
    public PaymentResponse(Integer id, OrderResponse order, PaymentMethods paymentMethod, PaymentOptions paymentOption,
                           String invoice, Float price, Float initialPayment, OffsetDateTime paymentDate) {
        this.id = id;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.paymentOption = paymentOption;
        this.invoice = invoice;
        this.price = price;
        this.initialPayment = initialPayment;
        this.paymentDate = paymentDate;
    }
}
