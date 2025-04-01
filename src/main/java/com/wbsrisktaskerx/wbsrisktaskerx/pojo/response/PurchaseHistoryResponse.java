package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentMethods;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.Date;

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
    OffsetDateTime purchaseDate;
    PaymentMethods paymentMethod;
    Float price;
    Integer warrantyMonths;
}
