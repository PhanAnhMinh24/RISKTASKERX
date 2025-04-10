package com.wbsrisktaskerx.wbsrisktaskerx.service.car;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CarResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PaymentResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.WarrantyResponse;

import java.util.List;

public interface ICarService {
    List<CarResponse> getCarByCustomerId(Integer customerId);
    PaymentResponse getPaymentByCarId(Integer carId);
    WarrantyResponse getWarrantyByCarId(Integer carId);
}
