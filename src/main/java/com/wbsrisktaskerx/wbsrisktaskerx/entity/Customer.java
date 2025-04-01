package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
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
    String phoneNumber;

    @Column(name = "address", length = 100, nullable = false)
    String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "tier", nullable = false, length = 10)
    Tier tier = Tier.Bronze;

    @Column(name = "is_active", nullable = false)
    Boolean  isActive = false;

    @Column(name = "date_of_birth", nullable = false)
    OffsetDateTime dateOfBirth;
}
