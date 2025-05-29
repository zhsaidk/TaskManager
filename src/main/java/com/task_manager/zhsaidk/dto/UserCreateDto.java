package com.task_manager.zhsaidk.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UserCreateDto {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String username;
    @NotBlank
    String password;
}
