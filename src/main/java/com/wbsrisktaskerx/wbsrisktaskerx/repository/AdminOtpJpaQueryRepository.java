package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdmin;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AdminOtpJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAdmin qAdmin = QAdmin.admin;

    public List<Admin> getAll() {
        return jpaQueryFactory.selectFrom(qAdmin).fetch();
    }

    public boolean existsByEmail(String email) {
        Admin admin = jpaQueryFactory
                .selectFrom(qAdmin)
                .where(qAdmin.email.eq(email))
                .fetchFirst();

        return ObjectUtils.isNotEmpty(admin);
    }
}