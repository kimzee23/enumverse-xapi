package org.enums.config;

import org.enums.client.XapiClient;
import org.enums.client.XapiClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;



@Configuration
public class XapiConfig {

    @Value("${xapi.lrs.url:http://localhost:8081/xapi}")
    private String lrsUrl;

    @Value("${xapi.lrs.username:admin}")
    private String username;

    @Value("${xapi.lrs.password:password}")
    private String password;

    @Bean
    public XapiClient xapiClient() {
        XapiClientConfig config = new XapiClientConfig(
                lrsUrl,
                username,
                password,
                30
        );
        return new XapiClient(config);
    }
}