package com.weekify.auth.dto;

import com.weekify.common.validation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SignUpRequest(

        @NotBlank(message = "{validation.email.required}")
        @Email(message = "{validation.email.invalid}")
        String email,

        @NotBlank(message = "{validation.password.required}")
        @ValidPassword
        String password,

        @NotBlank(message = "{validation.name.required}")
        @Size(max = 50, message = "{validation.name.size}")
        String name,

        @NotBlank(message = "{validation.tel.required}")
        String tel,

        // 2026-05-21 리뷰 오타 수정
        @NotNull(message = "{validation.birth-date.required}")
        LocalDate birthDate,

        @Schema(
                description = "주소",
                example = "서울시 강남구",
                nullable = false,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "{validation.address.required}")
        String address,

        String profileImageUrl
) {
}
