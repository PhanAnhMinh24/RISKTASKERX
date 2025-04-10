package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest {
    String key;
    String name;
    Integer orderNumber;
    Integer parentId;
}
