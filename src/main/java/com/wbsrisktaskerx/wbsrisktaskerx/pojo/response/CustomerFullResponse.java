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
public class CustomerFullResponse {
    Integer id;
    String fullName;
    String email;
    String address;
    String phoneNumber;
    Boolean isActive;
    Tier tier;
    String dateOfBirth;

    @QueryProjection
    public CustomerFullResponse(Integer id, String fullName, String email, String address, String phoneNumber, Boolean isActive, Tier tier, String dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.tier = tier;
        this.dateOfBirth = dateOfBirth;
    }
}
