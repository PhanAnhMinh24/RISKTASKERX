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
public class PurchaseHistoryResponse {
    Integer id;
    CustomerResponse customer;
    String vehicleIdentificationNumber;
    String carModel;
    OffsetDateTime purchaseDate;
    PaymentMethods paymentMethod;
    Float price;
    Integer warrantyMonths;

    @QueryProjection
    public PurchaseHistoryResponse(Integer id, CustomerResponse customer, String vehicleIdentificationNumber, String carModel, OffsetDateTime purchaseDate, PaymentMethods paymentMethod, Float price, Integer warrantyMonths) {
        this.id = id;
        this.customer = customer;
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.carModel = carModel;
        this.purchaseDate = purchaseDate;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.warrantyMonths = warrantyMonths;
    }
}
