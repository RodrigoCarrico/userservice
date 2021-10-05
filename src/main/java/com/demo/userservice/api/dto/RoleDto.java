package com.demo.userservice.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class RoleDto {
    @NonNull
    private String name;
}
