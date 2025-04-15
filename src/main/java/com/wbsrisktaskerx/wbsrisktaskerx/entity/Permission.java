package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = EntityConstant.PERMISSION_TABLE)
public class Permission extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "`key`", length = 50, nullable = false, unique = true)
    String key;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    String name;

    @Column(name = "order_number")
    Integer orderNumber;

    @Column(name = "parent_id")
    Integer parentId;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    List<RolePermission> rolePermissions;
}
