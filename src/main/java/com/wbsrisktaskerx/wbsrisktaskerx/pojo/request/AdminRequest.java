package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.DepartmentName;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ActiveRoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminRequest {
    Integer id;
    ActiveRoleResponse role;
    DepartmentName departmentName;
    String name;
    String phoneNumber;
    String email;
    OffsetDateTime dateOfBirth;
    Boolean isActive;
}
