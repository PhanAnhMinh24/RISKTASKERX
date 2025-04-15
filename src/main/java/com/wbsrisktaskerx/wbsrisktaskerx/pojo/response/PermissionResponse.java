package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse {
    Integer id;
    String key;
    String name;
    Integer orderNumber;
    Integer parentId;
    List<PermissionResponse> children;

    @QueryProjection
    public PermissionResponse(Integer id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }
}
