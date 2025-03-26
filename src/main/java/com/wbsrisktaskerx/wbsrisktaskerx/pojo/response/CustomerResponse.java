package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.Tier;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    Integer id;
    String fullName;
    String email;
    Integer phoneNumber;
    Boolean isActive;
    Tier tier;
    String dateOfBirth;
}
