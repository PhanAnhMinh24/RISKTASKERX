package com.wbsrisktaskerx.wbsrisktaskerx.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class RoleResponse {
    Integer id;
    String name;
    Boolean isActive;
    OffsetDateTime updateAt;
    List<PermissionResponse> permissions;

    @QueryProjection
    public RoleResponse(Integer id, String name, Boolean isActive, OffsetDateTime updateAt) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.updateAt = updateAt;
    }
}