package com.weekify.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(
                description = "이메일",
                example = "user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "{validation.email.required}")
        @Email(message = "{validation.email.invalid}")
        String email,

        @Schema(
                description = "비밀번호",
                example = "Password123!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "{validation.password.required}")
        String password
) {
}
