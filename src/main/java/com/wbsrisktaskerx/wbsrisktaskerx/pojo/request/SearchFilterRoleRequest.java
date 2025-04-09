package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchFilterRoleRequest {
    String searchKey;
    Boolean isActive;
}
