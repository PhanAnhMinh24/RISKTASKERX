package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarrantyHistoryResponse {
    Integer id;
    CustomerResponse customer;
    String carModel;
    String licensePlate;
    String serviceType;
    String serviceCenter;
    Date serviceDate;
    Long serviceCost;
}
