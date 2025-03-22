package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = EntityConstant.CUSTOMERS_TABLE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    String email;

    @Column(name = "full_name", length = 50, nullable = false)
    String fullName;

    @Column(name = "phone_number", length = 13, nullable = false)
    Integer phoneNumber;

    @Column(name = "address", length = 100, nullable = false)
    String address;

    @Column(name = "loyalty_points", length = 100, nullable = false)
    Integer loyaltyPoints;
}
