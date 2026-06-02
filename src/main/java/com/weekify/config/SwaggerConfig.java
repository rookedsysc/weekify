package com.weekify.config;


import io.swagger.v3.oas.models.Operation;

import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OperationCustomizer acceptLanguageHeaderCustomizer(){
        return (Operation operation, org.springframework.web.method.HandlerMethod handlerMethod) -> {
            Parameter acceptLanguageHeader = new Parameter()
                    .in("header")
                    .name("Accept-Language")
                    .description("응답 메시지 언어 설정: ko-KR 또는 en-US")
                    .required(false)
                    .schema(new StringSchema().example("ko-KR"));

            operation.addParametersItem(acceptLanguageHeader);

            return operation;
        };
    }
}
