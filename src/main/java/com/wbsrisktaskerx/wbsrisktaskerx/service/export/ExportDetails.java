package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ExportDetails {
    String password;
    String currentDate;
}
