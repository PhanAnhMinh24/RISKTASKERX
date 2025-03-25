package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterCustomersRequest {
    Tier tier;
    Boolean isActive;
    Integer page = 1;
    Integer size = 50;
}

