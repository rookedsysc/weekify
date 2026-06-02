package com.weekify.common.exception;

import java.util.List;

// 서버에서 발생한 예외를 클라이언트가 이해할 수 있는 일관된 JSON 형태로 바꾸기 위한 공통 에러 응답 DTO
public record ErrorResponse(
        String code,
        String message,
        List<FieldErrorResponse> errors
) {
    // ErrorCode의 code는 유지하고, 다국어 처리된 message를 주입한다.
    public static ErrorResponse of(ErrorCode errorCode, String message){
        return new ErrorResponse(
            errorCode.getCode(),
            message,
            List.of()
        );
    }

    // validation error처럼 field errors가 있는 경우 사용한다
    public static ErrorResponse of(
        ErrorCode errorCode,
        String message,
        List<FieldErrorResponse> errors
    ){
        return new ErrorResponse(
                errorCode.getCode(),
                message,
                errors
        );
    }
}
