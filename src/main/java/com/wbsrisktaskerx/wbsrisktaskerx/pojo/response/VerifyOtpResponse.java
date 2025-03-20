package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyOtpResponse {
    private boolean success;
    private String email;
}
