package com.weekify.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

// GlobalExceptionHandler에 @ExceptionHandler 기반의 예외 헨들러 메서드를 정의하며, 발생한 예외를 공통 ErrorResponse 형식으로 변환한다.
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource){
        this.messageSource = messageSource;
        System.out.println("🔥 GlobalExceptionHandler loaded");
    }

    private String getMessage(ErrorCode errorCode){
        return messageSource.getMessage(
                errorCode.getMessageKey(),
                null,
                errorCode.getCode(),
                LocaleContextHolder.getLocale()
        );
    }

    // BusinessException을 처리하는 예외 핸들러 메서드
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BaseException e){
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode, getMessage(errorCode)));
    }

    /*
    MethodArgumentNotValidException 예외 핸들러 메서드 별도 작성
    DTO의 유효성 검증 실패는 직접 작성하여 던지는 BusinessException이 아니라, Spring이 자동으로 발생시키는 MethodArgumentNotValidException 이기 때문
    MethodArgumentNotValidException은 BusinessException의 자식이 아님

    MethodArgumentNotValidException은 메시지를 꺼내는 방식도 다름
    검증 실패 메시지가 BindingResult 내에 들어가 있다 -> 응답 메시지를 제대로 내려주려면 별도의 예외 핸들러 메서드 필요

    JSON 파싱 성공 -> DTO 객체 생성 성공 -> @Valid 검증 실패 = MethodArgumentNotValidException
     */
    /*
    전역 예외 처리기는 MethodArgumentNotValidException에서 모든 FieldError를 꺼내 FieldErrorResponse 목록으로 변환한 뒤, ErrorResponse의 errors 필드에 담아 클라이언트에게 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
        ErrorCode errorCode = CommonErrorCode.INVALID_REQUEST;

        List<FieldErrorResponse> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorResponse(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode, getMessage(errorCode), errors));
    }

    /*
    JSON 형식 자체가 잘못된 경우는 별도의 예외 핸들러 메서드 작성 필요 : @Valid 검증 실패와 JSON 파싱 실패가 발셍하는 단계가 다름
    잘못된 JSON 형식
    -> DTO 객체 생성 자체가 실패
    -> @Valid 검증까지 도달하지 못함
    -> HttpMessageNotReadableException 발생
    즉, 예외 타입이 다르기 때문에 핸들러도 분리

    JSON 파싱 실패 또는 DTO 변환 실패 -> SignUpRequest 객체 생성 실패(@Valid 실행 불가) = HttpMessageNotReadableException
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException e
    ){
        ErrorCode errorCode = CommonErrorCode.INVALID_REQUEST_BODY;

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode, getMessage(errorCode)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        log.error("Unhandled exception occurred", e);

        ErrorCode errorCode = CommonErrorCode.UNKNOWN_ERROR;

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode, getMessage(errorCode)));
    }
}
