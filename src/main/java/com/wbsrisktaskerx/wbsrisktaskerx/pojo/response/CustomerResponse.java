package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.OffsetDateTime;

@Getter
@Setter
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.DATE_FORMAT)
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
