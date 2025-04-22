package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Installments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallmentRepository extends JpaRepository<Installments, Integer> {
    List<Installments> findByPaymentsId(Integer paymentsId);
    List<Installments> findByPaymentsIdIn(List<Integer> paymentsId);
}
