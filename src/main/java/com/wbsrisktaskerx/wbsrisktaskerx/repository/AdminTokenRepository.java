package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.AdminToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTokenRepository extends JpaRepository<AdminToken, Integer> {
}
