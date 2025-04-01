package com.wbsrisktaskerx.wbsrisktaskerx.service.customer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Customer;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.PurchaseHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.WarrantyHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.CustomerRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterCustomersRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.WarrantyHistoryRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerFullResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.WarrantyHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.CustomerRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.PurchaseHistoryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.WarrantyHistoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerJpaQueryRepository customerJpaQueryRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final WarrantyHistoryRepository warrantyHistoryRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerJpaQueryRepository customerJpaQueryRepository,
                               PurchaseHistoryRepository purchaseHistoryRepository, WarrantyHistoryRepository warrantyHistoryRepository) {
        this.customerRepository = customerRepository;
        this.customerJpaQueryRepository = customerJpaQueryRepository;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.warrantyHistoryRepository = warrantyHistoryRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerJpaQueryRepository.getAll();
    }

    @Override
    public Page<CustomerResponse> searchAndFilterCustomers(PagingRequest<SearchFilterCustomersRequest> request) {
        return customerJpaQueryRepository.searchedAndFilteredCustomers(request);
    }

    @Override
    public Page<CustomerFullResponse> fullSearchAndFilterCustomers(PagingRequest<SearchFilterCustomersRequest> request) {
        return customerJpaQueryRepository.fullSearchAndFilterCustomers(request);
    }

    @Override
    @Transactional
    public boolean updateIsActive(CustomerRequest request) {
        Customer customer = findById(request.getId());
        customer.setIsActive(request.getIsActive());
        customerRepository.save(customer);
        return Boolean.TRUE;
    }

    private Customer findById(Integer id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        return customer.get();
    }

    @Override
    public CustomerFullResponse getCustomerById(int id) {
        Customer customer = findById(id);
        return new CustomerFullResponse(
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

    public List<PurchaseHistory> getPurchaseHistoryById(int id) {
        return purchaseHistoryRepository.getPurchaseHistoryByCustomerId(id);
    }

    public List<WarrantyHistory> getWarrantyHistoryById(int id) {
        return warrantyHistoryRepository.getWarrantyHistoryByCustomerId(id);
    }

    public CustomerResponse findOneById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        Customer c = customer.get();
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
        Optional<Customer> customer = customerRepository.findById(warrantyHistoryRequest.getCustomerId());
        if (customer.isEmpty()) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        Customer c = customer.get();

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