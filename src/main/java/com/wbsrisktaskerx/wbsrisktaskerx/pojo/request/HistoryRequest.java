package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryRequest {
    Integer customerId;
}
