package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer id;
    AdminResponse admin;
    CustomerResponse customer;
    OrderStatus status;

    @QueryProjection
    public OrderResponse(Integer id, AdminResponse admin, CustomerResponse customer, OrderStatus status) {
        this.id = id;
        this.admin = admin;
        this.customer = customer;
        this.status = status;
    }
}
