package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.WarrantyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarrantyHistoryRepository extends JpaRepository<WarrantyHistory, Integer> {
    List<WarrantyHistory> getWarrantyHistoryByCustomerId(int id);
}
