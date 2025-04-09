package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchFilterRoleRequest {
    String searchKey;
    List<Boolean> isActive;
}
