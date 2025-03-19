package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.AdminOtp;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdmin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdminOtp;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.DateTimeUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AdminOtpJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @PersistenceContext
    private final EntityManager entityManager;
    private final QAdminOtp qAdminOtp = QAdminOtp.adminOtp;
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

    public Optional<Admin> findAdminByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jpaQueryFactory.selectFrom(qAdmin)
                            .where(qAdmin.email.eq(email))
                            .fetchOne()
            );
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Transactional
    public void saveOtp(AdminOtp adminOtp) {
        jpaQueryFactory.delete(qAdminOtp)
                        .where(qAdminOtp.admin.eq(adminOtp.getAdmin()))
                                .execute();
        entityManager.persist(adminOtp);
        entityManager.flush();
    }

    public Optional<AdminOtp> findValidOtpByEmail(String email, String otp) {
        try {
            BooleanExpression condition = qAdminOtp.admin.email.eq(email)
                    .and(qAdminOtp.expiresAt.after(DateTimeUtils.getDateTimeNow()));

            if (otp != null) {
                condition = condition.and(qAdminOtp.verificationCode.eq(otp));
            }

            return Optional.ofNullable(
                    jpaQueryFactory.selectFrom(qAdminOtp)
                            .where(condition)
                            .fetchOne()
            );
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteOtpByEmailAndCode(String email, String otp) {
        jpaQueryFactory.delete(qAdminOtp)
                .where(qAdminOtp.admin.email.eq(email)
                        .and(qAdminOtp.verificationCode.eq(otp)))
                .execute();
    }
}