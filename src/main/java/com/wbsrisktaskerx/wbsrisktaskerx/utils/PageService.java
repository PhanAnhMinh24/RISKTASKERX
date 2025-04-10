package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageService {
    public static <T> PageRequest getPageRequest(PagingRequest<T> requestPaging) {
        int pageIndex = (requestPaging.getPage() != null && requestPaging.getPage() > 0)
                ? requestPaging.getPage() - 1 : 0;
        int size = (requestPaging.getSize() != null && requestPaging.getSize() > 0)
                ? requestPaging.getSize() : 10;
        return StringUtils.isEmpty(requestPaging.getSortKey())
                ? PageRequest.of(pageIndex, size)
                : PageRequest.of(pageIndex, size,
                Sort.by(requestPaging.getSortBy(), new String[]{requestPaging.getSortKey()}));
    }
}
