package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDetailRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findById(int id);
}
