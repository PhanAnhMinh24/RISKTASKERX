package com.wbsrisktaskerx.wbsrisktaskerx.entity;


import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentMethods;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.ServiceCenter;
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

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    Car car;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    Payment payment;

    @OneToOne
    @JoinColumn(name = "warranty_id", nullable = false)
    Warranty warranty;

    @Column(name = "vehicle_identification_number")
    String vehicleIdentificationNumber;

    @Column(name = "purchase_date", nullable = false)
    OffsetDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    Admin seller;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_center")
    ServiceCenter serviceCenter;

}
