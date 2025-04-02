package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.PurchaseHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentMethods;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseHistoryResponse {
    Integer id;
    CustomerResponse customer;
    String vehicleIdentificationNumber;
    String carModel;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.DATE_FORMAT)
    OffsetDateTime purchaseDate;

    PaymentMethods paymentMethod;
    Float price;
    Integer warrantyMonths;
}
