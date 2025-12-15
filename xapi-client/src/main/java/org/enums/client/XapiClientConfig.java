package org.enums.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class XapiClientConfig {

    private final String endpoint;
    private final String username;
    private final String password;
    private final int timeoutSeconds;

}
