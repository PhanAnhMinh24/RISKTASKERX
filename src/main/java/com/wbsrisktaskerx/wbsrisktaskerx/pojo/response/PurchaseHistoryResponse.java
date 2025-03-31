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
public class PurchaseHistoryResponse {
    Integer id;
    CustomerResponse customer;
    String vehicleIdentificationNumber;
    String carModel;
    Date purchaseDate;
    String paymentMethod;
    Long price;
    Integer warrantyMonths;
}
