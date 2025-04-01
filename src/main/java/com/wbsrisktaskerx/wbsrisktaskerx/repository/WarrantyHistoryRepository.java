package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.WarrantyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarrantyHistoryRepository extends JpaRepository<WarrantyHistory, Integer> {
    List<WarrantyHistory> getWarrantyHistoryByCustomerId(int id);
}
