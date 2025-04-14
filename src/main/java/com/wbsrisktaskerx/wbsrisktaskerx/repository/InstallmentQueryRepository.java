package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.QInstallments;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.PaymentMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.InstallmentsResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public class InstallmentQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public InstallmentQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<InstallmentsResponse> getListInstallments(Integer id) {
        QInstallments installments = QInstallments.installments;
        return jpaQueryFactory
                .selectFrom(installments)
                .where(installments.payments.id.eq(id))
                .fetch()
                .stream()
                .map(PaymentMapper::installmentsMapper)
                .toList();
    }
}
