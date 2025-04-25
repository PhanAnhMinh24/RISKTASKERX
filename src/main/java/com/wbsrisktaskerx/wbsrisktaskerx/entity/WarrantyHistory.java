package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = EntityConstant.HISTORY_WARRANTY_TABLE)
public class WarrantyHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Column(name = "car_model", length = 100, nullable = false)
    String carModel;

    @Column(name = "license_plate", length = 9, nullable = false)
    String licensePlate;

    @Column(name = "service_type", length = 50, nullable = false)
    String serviceType;

    @Column(name = "service_center")
    String serviceCenter;

    @Column(name = "service_date", nullable = false)
    OffsetDateTime serviceDate;

    @Column(name = "service_cost", nullable = false)
    Float serviceCost;

}
