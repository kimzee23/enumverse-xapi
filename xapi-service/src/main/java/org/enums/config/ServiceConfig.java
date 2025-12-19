package org.enums.config;

package org.enums.config;

import org.enums.validation.XapiValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    // This creates the "Bean" that LrsServiceImpl is looking for!
    @Bean
    public XapiValidator xapiValidator() {
        return new XapiValidator();
    }
}