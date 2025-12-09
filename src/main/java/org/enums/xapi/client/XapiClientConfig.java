package org.enums.xapi.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

@Setter
@Getter

public class XapiClientConfig {

    private final String endpoint;
    private final String username;
    private final String password;
    private final int timeoutSeconds;


    public String getBasicAuthHeader(){
        if (username != null && password != null)return null;
        String raw  = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(raw.getBytes());
    }
}
