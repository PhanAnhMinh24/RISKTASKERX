package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdmin;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@AllArgsConstructor
public class AdminOtpJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAdmin qAdmin = QAdmin.admin;

    public List<Admin> getAll() {
        return jpaQueryFactory.selectFrom(qAdmin).fetch();
    }

    public boolean existsByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jpaQueryFactory.selectFrom(qAdmin)
                            .where(qAdmin.email.eq(email))
                            .fetchOne()
            ).isPresent();
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }

    }
}