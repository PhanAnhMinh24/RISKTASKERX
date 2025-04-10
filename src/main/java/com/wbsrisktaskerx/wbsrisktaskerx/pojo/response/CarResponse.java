package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarResponse {
    Integer id;
    CarBrandResponse carBrand;
    String model;
    String versionName;
    CarCategoryResponse category;
    Integer manufactureYear;
    Float price;
    String description;
    String imageUrl;
    Integer sparePartsId;
    AdminResponse seller;
    Boolean isActive;

    @QueryProjection
    public CarResponse(Integer id, CarBrandResponse carBrand, String model, String versionName,
                       CarCategoryResponse category, Integer manufactureYear, Float price,
                       String description, String imageUrl, Integer sparePartsId,
                       AdminResponse seller, Boolean isActive) {
        this.id = id;
        this.carBrand = carBrand;
        this.model = model;
        this.versionName = versionName;
        this.category = category;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sparePartsId = sparePartsId;
        this.seller = seller;
        this.isActive = isActive;
    }
}
