package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {
    List<PurchaseHistory> getPurchaseHistoryByCustomerId(int id);
}
