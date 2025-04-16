package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.DepartmentName;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AdminResponse {
    Integer id;
    String fullName;
    String email;
    String phoneNumber;
    Role role;
    DepartmentName departmentName;
    OffsetDateTime lastLogin;
    OffsetDateTime dayOfBirth;
    Boolean isActive;

    @QueryProjection
    public AdminResponse(Integer id, String fullName, String email, String phoneNumber, Role role,
                         DepartmentName departmentName, OffsetDateTime lastLogin, Boolean isActive) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.departmentName = departmentName;
        this.lastLogin = lastLogin;
        this.isActive = isActive;
    }
}
