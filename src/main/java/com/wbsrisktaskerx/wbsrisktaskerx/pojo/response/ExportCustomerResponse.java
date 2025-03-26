package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExportCustomerResponse {
    InputStreamResource response;
    String fileName;
}
