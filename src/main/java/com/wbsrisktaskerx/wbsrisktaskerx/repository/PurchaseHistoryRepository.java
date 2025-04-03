package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {
}
