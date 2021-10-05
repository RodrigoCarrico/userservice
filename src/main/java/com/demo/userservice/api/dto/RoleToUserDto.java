package com.demo.userservice.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class RoleToUserDto {
    private final String username;
    private final String roleName;
}
