package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddRoleRequest {
    String name;
    Boolean isActive = false;
    List<Integer> permissionId;
}
