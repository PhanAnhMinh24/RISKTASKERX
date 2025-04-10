package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarCategoryResponse {
    Integer id;
    String name;

    @QueryProjection
    public CarCategoryResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
