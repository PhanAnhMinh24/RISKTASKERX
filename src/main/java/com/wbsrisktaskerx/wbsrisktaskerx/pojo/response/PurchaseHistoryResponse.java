package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.ServiceCenter;
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
    CarResponse car;
    PaymentResponse payment;
    WarrantyResponse warranty;
    String vehicleIdentificationNumber;
    OffsetDateTime purchaseDate;
    AdminResponse admin;
    ServiceCenter serviceCenter;

    @QueryProjection
    public PurchaseHistoryResponse(Integer id, CustomerResponse customer, CarResponse car, PaymentResponse payment,
                                   WarrantyResponse warranty, String vehicleIdentificationNumber,
                                   OffsetDateTime purchaseDate, AdminResponse admin, ServiceCenter serviceCenter) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.payment = payment;
        this.warranty = warranty;
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.purchaseDate = purchaseDate;
        this.admin = admin;
        this.serviceCenter = serviceCenter;
    }
}
