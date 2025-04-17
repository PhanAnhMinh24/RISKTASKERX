package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    int id;
    String name;
    Boolean isActive;
    List<Integer> permissionId;
}