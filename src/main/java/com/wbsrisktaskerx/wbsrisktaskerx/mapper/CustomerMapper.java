package com.wbsrisktaskerx.wbsrisktaskerx.mapper;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;

public class CustomerMapper {
    public static CustomerResponse customerMapper(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .isActive(customer.getIsActive())
                .tier(customer.getTier())
                .dateOfBirth(customer.getDateOfBirth())
                .build();
    }
}
