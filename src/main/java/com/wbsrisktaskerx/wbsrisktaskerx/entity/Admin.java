package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.DepartmentName;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = EntityConstant.ADMIN_TABLE)
public class Admin extends BaseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "full_name", length = 50, nullable = false)
    String fullName;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    String email;

    @Column(name = "phone_number", length = 15, nullable = false)
    String phoneNumber;

    @Column(name = "password", length = 255, nullable = false)
    String password;

    @Column(name = "profile_img", length = 255)
    String profileImg;

    @Builder.Default
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    Boolean isActive = true;

    @Column(name = "role_id", insertable = false, updatable = false, nullable = false)
    Integer roleId;

    @Column(name = "last_login", nullable = false)
    OffsetDateTime lastLogin;

    @Column(name = "date_of_birth", nullable = false)
    OffsetDateTime dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "department_name", nullable = false, length = 10)
    DepartmentName departmentName;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<AdminOtp> otp;
}
