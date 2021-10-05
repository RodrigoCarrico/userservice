package com.demo.userservice.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserDto {

    @NonNull
    private String name;

    @NonNull
    private String username;

    @NonNull
    private String password;
}
