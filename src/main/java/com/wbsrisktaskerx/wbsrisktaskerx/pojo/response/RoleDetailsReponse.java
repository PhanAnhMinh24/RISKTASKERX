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
public class RoleDetailsReponse {
    Integer id;
    String name;
    Boolean isActive;
    List<String> permissionNames;

    @QueryProjection
    public RoleDetailsReponse(Integer id, String name, Boolean isActive, List<String> permissionNames) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.permissionNames = permissionNames;
    }
}
