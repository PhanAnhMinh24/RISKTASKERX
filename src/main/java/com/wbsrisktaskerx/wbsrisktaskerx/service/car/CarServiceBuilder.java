package com.wbsrisktaskerx.wbsrisktaskerx.service.car;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.*;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.*;

public class CarServiceBuilder {
    public static CarBrandResponse carBrandBuilder(CarBrand carBrand) {
        return CarBrandResponse.builder()
                .id(carBrand.getId())
                .name(carBrand.getName())
                .build();
    }

    public static CarCategoryResponse carCategoryBuilder(CarCategory carCategory) {
        return CarCategoryResponse.builder()
                .id(carCategory.getId())
                .name(carCategory.getName())
                .build();
    }

    public static CarResponse carBuilder(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .carBrand(carBrandBuilder(car.getBrand()))
                .model(car.getModel())
                .versionName(car.getVersionName())
                .category(carCategoryBuilder(car.getCategory()))
                .manufactureYear(car.getManufactureYear())
                .price(car.getPrice())
                .description(car.getDescription())
                .imageUrl(car.getImageUrl())
                .sparePartsId(car.getSparePartsId())
                .seller(adminBuilder(car.getSeller()))
                .isActive(car.getIsActive())
                .build();
    }

    public static AdminResponse adminBuilder(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .role(admin.getRole())
                .departmentName(admin.getDepartmentName())
                .lastLogin(admin.getLastLogin())
                .isActive(admin.getIsActive())
                .build();
    }

    public static CustomerResponse customerBuilder(Customer customer) {
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

    public static OrderResponse orderBuilder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .admin(adminBuilder(order.getAdmin()))
                .customer(customerBuilder(order.getCustomer()))
                .status(order.getStatus())
                .build();
    }

    public static PaymentResponse paymentBuilder(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .order(orderBuilder(payment.getOrder()))
                .paymentMethod(payment.getPaymentMethod())
                .paymentOption(payment.getPaymentOption())
                .invoice(payment.getInvoice())
                .price(payment.getPrice())
                .initialPayment(payment.getInitialPayment())
                .installmentAmount(payment.getInstallmentAmount())
                .installmentPlan(payment.getInstallmentPlan())
                .remainingInstallmentMonths(payment.getRemainingInstallmentMonths())
                .monthlyPayment(payment.getMonthlyPayment())
                .dueDate(payment.getDueDate())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

    public static WarrantyResponse warrantyBuilder(Warranty warranty) {
        return WarrantyResponse.builder()
                .id(warranty.getId())
                .car(carBuilder(warranty.getCar()))
                .customer(customerBuilder(warranty.getCustomer()))
                .startedDate(warranty.getStartedDate())
                .expiredDate(warranty.getExpiredDate())
                .build();
    }

    public static PurchaseHistoryResponse purchaseHistoryBuilder(PurchaseHistory purchaseHistory) {
        return PurchaseHistoryResponse.builder()
                .id(purchaseHistory.getId())
                .customer(customerBuilder(purchaseHistory.getCustomer()))
                .car(carBuilder(purchaseHistory.getCar()))
                .payment(paymentBuilder(purchaseHistory.getPayment()))
                .warranty(warrantyBuilder(purchaseHistory.getWarranty()))
                .vehicleIdentificationNumber(purchaseHistory.getVehicleIdentificationNumber())
                .purchaseDate(purchaseHistory.getPurchaseDate())
                .salesRepresentative(purchaseHistory.getSalesRepresentative())
                .serviceCenter(purchaseHistory.getServiceCenter())
                .build();
    }

    public static WarrantyHistoryResponse warrantyHistoryBuilder(WarrantyHistory warrantyHistory) {
        return WarrantyHistoryResponse.builder()
                .id(warrantyHistory.getId())
                .customer(customerBuilder(warrantyHistory.getCustomer()))
                .carModel(warrantyHistory.getCarModel())
                .licensePlate(warrantyHistory.getLicensePlate())
                .serviceType(warrantyHistory.getServiceType())
                .serviceCenter(warrantyHistory.getServiceCenter())
                .serviceDate(warrantyHistory.getServiceDate())
                .serviceCost(warrantyHistory.getServiceCost())
                .build();
    }
}
