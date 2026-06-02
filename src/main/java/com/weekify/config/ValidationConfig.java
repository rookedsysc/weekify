package com.weekify.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@RequiredArgsConstructor
public class ValidationConfig implements WebMvcConfigurer {

    private final MessageSource messageSource;

    @Bean
    public LocalValidatorFactoryBean validator(){
        LocalValidatorFactoryBean validatorFactoryBean =
                new LocalValidatorFactoryBean();

        validatorFactoryBean.setValidationMessageSource(messageSource);

        return validatorFactoryBean;
    }

    @Override
    public Validator getValidator(){
        return validator();
    }
}
