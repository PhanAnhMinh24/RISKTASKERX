package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.FillConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.StringConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdmin;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.AdminMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
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
import java.util.stream.Collectors;

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

        if (!ObjectUtils.isEmpty(filter.getIsActive())) {
            builder.and(admin.isActive.in(filter.getIsActive()));
        }

        Order direction = Optional.of(pageable.getSort())
                .map(sort -> sort.getOrderFor(FillConstants.LAST_LOGIN))
                .map(order -> order.isDescending() ? Order.DESC : Order.ASC)
                .orElse(Order.ASC);

        List<AdminResponse> content = jpaQueryFactory.selectFrom(admin)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(new OrderSpecifier<>(direction, admin.lastLogin))
                .fetch()
                .stream()
                .map(AdminMapper::adminMapper)
                .collect(Collectors.toList());

        long total = Optional.ofNullable(
                jpaQueryFactory.select(admin.id.count())
                        .from(admin)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }


    public List<AdminResponse> searchedAndFilteredAdminNoPaging(SearchFilterAdminRequest filter) {
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

        if (!ObjectUtils.isEmpty(filter.getIsActive())) {
            builder.and(admin.isActive.in(filter.getIsActive()));
        }

        return jpaQueryFactory.selectFrom(admin)
                .where(builder)
                .orderBy(admin.lastLogin.desc())
                .fetch()
                .stream()
                .map(AdminMapper::adminMapper)
                .collect(Collectors.toList());
    }

}
