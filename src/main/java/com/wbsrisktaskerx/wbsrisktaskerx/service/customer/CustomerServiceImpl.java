package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.AdminToken;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.WarrantyHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.CustomerRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.WarrantyHistoryRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.*;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.MaskUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerJpaQueryRepository customerJpaQueryRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final WarrantyHistoryRepository warrantyHistoryRepository;
    private final AdminTokenRepository adminTokenRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerJpaQueryRepository customerJpaQueryRepository,
                               PurchaseHistoryRepository purchaseHistoryRepository,
                               WarrantyHistoryRepository warrantyHistoryRepository,
                               AdminTokenRepository adminTokenRepository) {
        this.customerRepository = customerRepository;
        this.customerJpaQueryRepository = customerJpaQueryRepository;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.warrantyHistoryRepository = warrantyHistoryRepository;
        this.adminTokenRepository = adminTokenRepository;
    }

    private void validateToken(String accessToken) {
        Optional<AdminToken> token = adminTokenRepository.findByAccessToken(accessToken);
        if (token.isEmpty() || token.get().getExpiresAt().isBefore(OffsetDateTime.now(ZoneOffset.UTC))) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
    @Override
    public Page<CustomerResponse> searchAndFilterCustomers(PagingRequest<SearchFilterCustomersRequest> request) {
        return customerJpaQueryRepository.searchedAndFilteredCustomers(request)
                .map(cr -> {
                    cr.setFullName(MaskUtils.mask(cr.getFullName()));
                    cr.setEmail(MaskUtils.mask(cr.getEmail()));
                    cr.setAddress(MaskUtils.mask(cr.getAddress()));
                    cr.setPhoneNumber(MaskUtils.mask(cr.getPhoneNumber()));
                    return cr;
                });
    }

    @Override
    public CustomerResponse getCustomerById(int id) {
        Customer customer = findCustomerById(id);
        return new CustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getIsActive(),
                customer.getTier(),
                customer.getDateOfBirth()
        );
    }

    @Override
    @Transactional
    public boolean updateIsActive(CustomerRequest request) {
        Customer customer = findById(request.getId());
        customer.setIsActive(request.getIsActive());
        customerRepository.save(customer);
        return Boolean.TRUE;
    }

    public Customer findById(Integer id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        return customer.get();
    }

    public Customer findCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        return customer.get();
    }

    public CustomerResponse findOneById(Integer customerId) {
        Customer c = findCustomerById(customerId);
        return new CustomerResponse(
                c.getId(),
                c.getFullName(),
                c.getEmail(),
                c.getAddress(),
                c.getPhoneNumber(),
                c.getIsActive(),
                c.getTier(),
                c.getDateOfBirth()
        );
    }

    @Override
    public void addWarrantyHistory(WarrantyHistoryRequest warrantyHistoryRequest) {
        Customer c = findCustomerById(warrantyHistoryRequest.getCustomerId());
        WarrantyHistory warrantyHistory = WarrantyHistory.builder()
                .customer(c)
                .carModel(warrantyHistoryRequest.getCarModel())
                .licensePlate(warrantyHistoryRequest.getLicensePlate())
                .serviceType(warrantyHistoryRequest.getServiceType())
                .serviceCenter(warrantyHistoryRequest.getServiceCenter())
                .serviceDate(warrantyHistoryRequest.getServiceDate())
                .serviceCost(warrantyHistoryRequest.getServiceCost())
                .build();
        warrantyHistoryRepository.save(warrantyHistory);
    }
}