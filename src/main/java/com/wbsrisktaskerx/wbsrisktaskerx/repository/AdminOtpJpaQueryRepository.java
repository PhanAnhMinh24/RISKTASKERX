package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.AdminOtp;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdmin.admin;
import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdminOtp.adminOtp;

@Component
@AllArgsConstructor
public class AdminOtpJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final AdminOtpRepository adminOtpRepository;

    public List<Admin> getAll() {
        return jpaQueryFactory.selectFrom(admin).fetch();
    }

    public boolean existsByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jpaQueryFactory.selectFrom(admin)
                            .where(admin.email.eq(email))
                            .fetchOne()
            ).isPresent();
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    public Optional<Admin> findAdminByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jpaQueryFactory.selectFrom(admin)
                            .where(admin.email.eq(email))
                            .fetchOne()
            );
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Transactional
    public void saveOtp(AdminOtp otp) {
        adminOtpRepository.deleteByAdmin(otp.getAdmin());
        adminOtpRepository.save(otp);
    }

    public Optional<AdminOtp> findValidOtpByEmail(String email, String otp) {
        try {
            BooleanExpression condition = adminOtp.admin.email.eq(email)
                    .and(adminOtp.expiresAt.after(DateTimeUtils.getDateTimeNow()));

            if (org.apache.commons.lang3.StringUtils.isNotBlank(otp)) {
                condition = condition.and(adminOtp.verificationCode.eq(otp));
            }

            return Optional.ofNullable(
                    jpaQueryFactory.selectFrom(adminOtp)
                            .where(condition)
                            .fetchOne()
            );
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteOtpByEmailAndCode(String email, String otp) {
        jpaQueryFactory.delete(adminOtp)
                .where(adminOtp.admin.email.eq(email)
                        .and(adminOtp.verificationCode.eq(otp)))
                .execute();
    }
}
