package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarrantyHistoryResponse {
    Integer id;
    CustomerResponse customer;
    String carModel;
    String licensePlate;
    String serviceType;
    String serviceCenter;
    OffsetDateTime serviceDate;
    Float serviceCost;

    @QueryProjection
    public WarrantyHistoryResponse(Integer id, CustomerResponse customer, String carModel, String licensePlate,
                                   String serviceType, String serviceCenter, OffsetDateTime serviceDate,
                                   Float serviceCost) {
        this.id = id;
        this.customer = customer;
        this.carModel = carModel;
        this.licensePlate = licensePlate;
        this.serviceType = serviceType;
        this.serviceCenter = serviceCenter;
        this.serviceDate = serviceDate;
        this.serviceCost = serviceCost;
    }
}
