package com.wbsrisktaskerx.wbsrisktaskerx.entity;


import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

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

    @Column(name = "car_model", length = 50, nullable = false)
    String carModel;

    @Column(name = "purchase_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "warranty_months", nullable = false)
    private Integer warrantyMonths;

}
