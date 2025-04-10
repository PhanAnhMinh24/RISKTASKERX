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
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "warranty_id", nullable = false)
    private Warranty warranty;

    @Column(name = "vehicle_identification_number")
    private String vehicleIdentificationNumber;

    @Column(name = "purchase_date", nullable = false)
    private OffsetDateTime purchaseDate;

    @Column(name = "sales_representative")
    private String salesRepresentative;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_center")
    private ServiceCenter serviceCenter;

}
