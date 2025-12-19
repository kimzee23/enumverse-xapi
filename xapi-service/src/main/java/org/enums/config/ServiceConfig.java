package org.enums.config;

import org.enums.validation.XapiValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public XapiValidator xapiValidator() {
        return new XapiValidator();
    }
}