package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    Integer id;
    String fullName;
    String email;
    String address;
    String phoneNumber;
    Boolean isActive;
    Tier tier;
    OffsetDateTime dateOfBirth;

    @QueryProjection
    public CustomerResponse(Integer id, String fullName, String email, String address, String phoneNumber, Boolean isActive, Tier tier, OffsetDateTime dateOfBirth) {
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
