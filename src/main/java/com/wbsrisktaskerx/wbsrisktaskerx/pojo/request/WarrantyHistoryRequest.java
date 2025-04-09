package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarrantyHistoryRequest {
    Integer customerId;
    String carModel;
    String licensePlate;
    String serviceType;
    String serviceCenter;
    OffsetDateTime serviceDate;
    Float serviceCost;
}