package org.enums.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class XapiClientConfig {

    private String endpoint;
    private String username;
    private String password;
    private int timeoutSeconds;

    /** Build Authorization header */
    public String getBasicAuthHeader() {
        String raw = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder()
                .encodeToString(raw.getBytes());
    }
}
