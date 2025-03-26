package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    Integer id;
    String fullName;
    String email;
    String phoneNumber;
    Boolean isActive;
    Tier tier;

    @QueryProjection
    public CustomerResponse(Integer id, String fullName, String email, String phoneNumber, Boolean isActive, Tier tier) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.tier = tier;
    }
}
