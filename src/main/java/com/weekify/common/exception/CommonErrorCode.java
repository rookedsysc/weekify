package com.weekify.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum CommonErrorCode implements ErrorCode {

    INVALID_REQUEST(
            HttpStatus.BAD_REQUEST,
            "COMMON_001",
            "common.invalid-request"
    ),

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "COMMON_002",
            "common.internal-server-error"

    ),

    UNKNOWN_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "COMMON_003",
            "common.unknown-error"
    ),

    INVALID_REQUEST_BODY(
            HttpStatus.BAD_REQUEST,
            "COMMON_004",
            "common.invalid-request-body"
    )
    ;

    private final HttpStatus status;
    private final String code;
    private final String messageKey;

    CommonErrorCode(HttpStatus status, String code, String messageKey){
        this.status = status;
        this.code = code;
        this.messageKey = messageKey;
    }
}
