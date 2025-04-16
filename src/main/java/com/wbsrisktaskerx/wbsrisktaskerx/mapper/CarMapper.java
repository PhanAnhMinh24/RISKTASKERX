package com.wbsrisktaskerx.wbsrisktaskerx.mapper;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.*;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.*;

import static com.wbsrisktaskerx.wbsrisktaskerx.mapper.CustomerMapper.customerMapper;

public class CarMapper {

    public static CarBrandResponse carBrandMapper(CarBrand carBrand) {
        return CarBrandResponse.builder()
                .id(carBrand.getId())
                .name(carBrand.getName())
                .build();
    }

    public static CarCategoryResponse carCategoryMapper(CarCategory carCategory) {
        return CarCategoryResponse.builder()
                .id(carCategory.getId())
                .name(carCategory.getName())
                .build();
    }

    public static CarResponse carMapper(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .carBrand(carBrandMapper(car.getBrand()))
                .model(car.getModel())
                .versionName(car.getVersionName())
                .category(carCategoryMapper(car.getCategory()))
                .manufactureYear(car.getManufactureYear())
                .price(car.getPrice())
                .description(car.getDescription())
                .imageUrl(car.getImageUrl())
                .isActive(car.getIsActive())
                .build();
    }

    public static WarrantyResponse warrantyMapper(Warranty warranty) {
        return WarrantyResponse.builder()
                .id(warranty.getId())
                .car(carMapper(warranty.getCar()))
                .customer(customerMapper(warranty.getCustomer()))
                .startedDate(warranty.getStartedDate())
                .expiredDate(warranty.getExpiredDate())
                .build();
    }
}
