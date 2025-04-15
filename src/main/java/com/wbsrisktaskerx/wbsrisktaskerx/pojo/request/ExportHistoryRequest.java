package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExportHistoryRequest {
    Integer id;
    Integer paymentsId;
}
