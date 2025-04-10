package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.StringConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QRole;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterRoleRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.QRoleResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.RoleResponse;
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

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QRole.role;

@Component
public class RoleJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QRole qRole = role;

    public RoleJpaQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<RoleResponse> searchedAndFilteredRole(PagingRequest<SearchFilterRoleRequest> request) {
        SearchFilterRoleRequest filter = request.getFilters();
        Pageable pageable = PageService.getPageRequest(request);
        String searchKey = filter.getSearchKey();

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotBlank(searchKey)) {
            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.or(role.name.like(
                    String.format(StringConstants.PERCENT, CommonConstants.WILDCARD, searchKey, CommonConstants.WILDCARD)
            ));
            if (NumberUtils.isCreatable(searchKey)) {
                Integer idValue = Integer.valueOf(searchKey);
                searchBuilder.or(role.id.eq(idValue));
            }
            builder.and(searchBuilder);
        }

        if (!ObjectUtils.isEmpty(filter.getIsActive())) {
            builder.and(role.isActive.in(filter.getIsActive()));
        }

        List<RoleResponse> content = jpaQueryFactory.select(
                        new QRoleResponse(
                                role.id,
                                role.name,
                                role.isActive,
                                role.updateAt
                        ))
                .from(role)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory.select(role.id.count())
                        .from(role)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }

}
