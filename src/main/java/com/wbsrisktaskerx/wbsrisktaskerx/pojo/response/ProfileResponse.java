package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {
    Long id;
    String fullName;
    String email;
    String phoneNumber;
    String profileImg;
    Boolean isActive;
}
