package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExportAdminResponse {
    byte[] response;
    String fileName;
    String password;
}
