package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.StringConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdmin;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.QAdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.PageService;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdminJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAdmin admin = QAdmin.admin;
    public AdminJpaQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<AdminResponse> searchedAndFilteredAdmin(PagingRequest<SearchFilterAdminRequest> request) {
        SearchFilterAdminRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        String searchKey = filter.getSearchKey();

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotBlank(searchKey)) {
            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.or(admin.fullName.like(
                    String.format(StringConstants.PERCENT, CommonConstants.WILDCARD, searchKey, CommonConstants.WILDCARD)
            ));
            if (NumberUtils.isCreatable(searchKey)) {
                Integer idValue = Integer.valueOf(searchKey);
                searchBuilder.or(admin.id.eq(idValue));
            }
            builder.and(searchBuilder);
        }

        if (!ObjectUtils.isEmpty(filter.getDepartmentName())) {
            builder.and(admin.departmentName.in(filter.getDepartmentName()));
        }

        List<AdminResponse> content = jpaQueryFactory.select(
                        new QAdminResponse(
                                admin.id,
                                admin.fullName,
                                admin.email,
                                admin.phoneNumber,
                                admin.role,
                                admin.departmentName,
                                admin.lastLogin,
                                admin.isActive
                        ))
                .from(admin)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory.select(admin.id.count())
                        .from(admin)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }
}
