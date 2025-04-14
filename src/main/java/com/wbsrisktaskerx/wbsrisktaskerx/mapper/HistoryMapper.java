package com.wbsrisktaskerx.wbsrisktaskerx.mapper;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.PurchaseHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.WarrantyHistory;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PurchaseHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.WarrantyHistoryResponse;

import static com.wbsrisktaskerx.wbsrisktaskerx.mapper.AdminMapper.adminMapper;
import static com.wbsrisktaskerx.wbsrisktaskerx.mapper.CarMapper.carMapper;
import static com.wbsrisktaskerx.wbsrisktaskerx.mapper.CarMapper.warrantyMapper;
import static com.wbsrisktaskerx.wbsrisktaskerx.mapper.CustomerMapper.customerMapper;
import static com.wbsrisktaskerx.wbsrisktaskerx.mapper.PaymentMapper.paymentMapper;

public class HistoryMapper {
    public static PurchaseHistoryResponse purchaseHistoryMapper(PurchaseHistory purchaseHistory) {
        return PurchaseHistoryResponse.builder()
                .id(purchaseHistory.getId())
                .customer(customerMapper(purchaseHistory.getCustomer()))
                .car(carMapper(purchaseHistory.getCar()))
                .payment(paymentMapper(purchaseHistory.getPayment()))
                .warranty(warrantyMapper(purchaseHistory.getWarranty()))
                .vehicleIdentificationNumber(purchaseHistory.getVehicleIdentificationNumber())
                .purchaseDate(purchaseHistory.getPurchaseDate())
                .admin(adminMapper(purchaseHistory.getSeller()))
                .serviceCenter(purchaseHistory.getServiceCenter())
                .build();
    }

    public static WarrantyHistoryResponse warrantyHistoryMapper(WarrantyHistory warrantyHistory) {
        return WarrantyHistoryResponse.builder()
                .id(warrantyHistory.getId())
                .customer(customerMapper(warrantyHistory.getCustomer()))
                .carModel(warrantyHistory.getCarModel())
                .licensePlate(warrantyHistory.getLicensePlate())
                .serviceType(warrantyHistory.getServiceType())
                .serviceCenter(warrantyHistory.getServiceCenter())
                .serviceDate(warrantyHistory.getServiceDate())
                .serviceCost(warrantyHistory.getServiceCost())
                .build();
    }
}
