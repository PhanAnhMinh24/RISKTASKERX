package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Car;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarrantyResponse {
    Integer id;
    CarResponse car;
    CustomerResponse customer;
    OffsetDateTime startedDate;
    OffsetDateTime expiredDate;

    @QueryProjection
    public WarrantyResponse(Integer id, CarResponse car, CustomerResponse customer,
                            OffsetDateTime startedDate, OffsetDateTime expiredDate) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.startedDate = startedDate;
        this.expiredDate = expiredDate;
    }
}
