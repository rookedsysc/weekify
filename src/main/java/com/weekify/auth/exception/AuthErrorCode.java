package com.weekify.auth.exception;

import com.weekify.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements ErrorCode {
    DUPLICATED_EMAIL(
            HttpStatus.CONFLICT,
            "AUTH_001",
            "auth.duplicated-email"
    ),
    // 로그인 실패 시 이메일 존재 여부나 비밀번호 불일치 여부가 노출되지 않도록 INVALID_LOGIN_CREDENTIALS 하나로 통합해서 처리하는 현재 방식이 적절하다고 판단
    // "이메일 또는 비밀번호가 올바르지 않습니다." 메시지 유지 -> DTO validation 실페는 별도로 필드별 에러 메시지를 내려주는 방식으로 구분
    INVALID_LOGIN_CREDENTIALS(
            HttpStatus.UNAUTHORIZED,
            "AUTH_002",
            "auth.invalid-login-credentials"
    ),

    EXPIRED_REFRESH_TOKEN(
            HttpStatus.UNAUTHORIZED,
            "AUTH_003",
            "auth.expired-refresh-token"
    ),

    INVALID_REFRESH_TOKEN(
      HttpStatus.UNAUTHORIZED,
      "AUTH_004",
      "auth.invalid-refresh-token"
    ),

    REUSED_REFRESH_TOKEN(
            HttpStatus.UNAUTHORIZED,
            "AUTH_005",
            "auth.reused-refresh-token"
    )
    ;

    private final HttpStatus status;
    private final String code;
    private final String messageKey;

    AuthErrorCode(HttpStatus status, String code, String messageKey){
        this.status = status;
        this.code = code;
        this.messageKey = messageKey;
    }
}
