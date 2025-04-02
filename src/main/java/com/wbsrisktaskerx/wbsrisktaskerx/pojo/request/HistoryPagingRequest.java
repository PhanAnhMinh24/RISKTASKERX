package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryPagingRequest {
    private Integer page = 1;
    private Integer size = 10;
    private String sortKey;
    private Sort.Direction sortBy = Sort.Direction.DESC;

}
