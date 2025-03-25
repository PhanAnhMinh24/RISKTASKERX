package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCustomersRequest {
    Integer id;
    String fullName;
    Integer page = 1;
    Integer size = 50;
}
