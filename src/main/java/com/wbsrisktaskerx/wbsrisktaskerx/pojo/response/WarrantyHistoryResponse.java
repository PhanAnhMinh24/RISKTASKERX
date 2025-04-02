package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.WarrantyHistory;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.DATE_FORMAT)
    OffsetDateTime serviceDate;

    Float serviceCost;
}
