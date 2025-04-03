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
@Table(name = EntityConstant.ROLES_TABLE)
public class Role extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    List<Admin> admins;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    List<RolePermission> rolePermissions;
}
