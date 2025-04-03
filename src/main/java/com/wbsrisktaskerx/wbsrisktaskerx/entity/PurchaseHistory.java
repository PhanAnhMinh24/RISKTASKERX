package com.wbsrisktaskerx.wbsrisktaskerx.entity;


import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentMethods;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = EntityConstant.HISTORY_PURCHASE_TABLE)
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Column(name = "vehicle_identification_number", length = 20, nullable = false, unique = true)
    String vehicleIdentificationNumber;

    @Column(name = "car_model", length = 50, nullable = false)
    String carModel;

    @Column(name = "purchase_date", nullable = false)
    OffsetDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    PaymentMethods paymentMethod;

    @Column(name = "price", nullable = false)
    Float price;

    @Column(name = "warranty_months", nullable = false)
    Integer warrantyMonths;

}
