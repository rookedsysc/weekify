package com.weekify.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
// 해당 어노테이션이 붙은 값은 PasswordValidator 클래스로 검증
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "{validation.password.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
