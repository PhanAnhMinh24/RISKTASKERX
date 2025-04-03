package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;
}
