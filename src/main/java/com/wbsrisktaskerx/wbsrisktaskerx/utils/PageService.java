package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Sort;

public class PageService {
    public static <T> JPAQuery<T> applyPagination(JPAQuery<T> query, Integer page, Integer size, String sortKey, Sort.Direction sortBy) {
            int currentPage = (page != null && page > 0) ? page : 1;
            int pageSize = (size != null && size > 0) ? size : 50;
            int offset = (currentPage - 1) * pageSize;
            query.offset(offset).limit(pageSize);
            return query;
    }
}
