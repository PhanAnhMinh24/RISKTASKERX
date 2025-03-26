package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDetailsRepository extends JpaRepository<Customer, Integer> {
    public Optional<Customer> findById (int id);
}
