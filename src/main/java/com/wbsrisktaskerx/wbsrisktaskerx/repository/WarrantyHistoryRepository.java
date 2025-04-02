package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.WarrantyHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarrantyHistoryRepository extends JpaRepository<WarrantyHistory, Integer> {
    Page<WarrantyHistory> findByCustomerId(int id, Pageable pageable);
}
