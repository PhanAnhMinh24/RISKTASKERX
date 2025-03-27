package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.core.annotations.QueryProjection;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import com.wbsrisktaskerx.wbsrisktaskerx.serializer.MaskingAddressSerializer;
import com.wbsrisktaskerx.wbsrisktaskerx.serializer.MaskingDateOfBirthSerializer;
import com.wbsrisktaskerx.wbsrisktaskerx.serializer.MaskingEmailSerializer;
import com.wbsrisktaskerx.wbsrisktaskerx.serializer.MaskingFullNameSerializer;
import com.wbsrisktaskerx.wbsrisktaskerx.serializer.MaskingPhoneNumberSerializer;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    Integer id;

    @JsonSerialize(using = MaskingFullNameSerializer.class)
    String fullName;

    @JsonSerialize(using = MaskingEmailSerializer.class)
    String email;

    @JsonSerialize(using = MaskingAddressSerializer.class)
    String address;

    @JsonSerialize(using = MaskingPhoneNumberSerializer.class)
    String phoneNumber;

    Boolean isActive;
    Tier tier;

    @JsonSerialize(using = MaskingDateOfBirthSerializer.class)
    String dateOfBirth;

    @QueryProjection
    public CustomerResponse(Integer id, String fullName, String email, String address, String phoneNumber, Boolean isActive, Tier tier, String dateOfBirth) {
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
