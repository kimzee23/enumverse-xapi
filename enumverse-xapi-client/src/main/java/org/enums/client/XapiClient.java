package org.enums.client;

import org.enums.exception.XapiClientException;
import org.enums.xapi.model.XapiStatement;
import java.net.http.*;
import java.net.URI;

public class XapiClient {

    private final String endpoint;
    private final String username;
    private final String password;
    private final XapiClientConfig config;
    private final HttpClient httpClient;

    public XapiClient(String endpoint, String username, String password, XapiClientConfig config) {
        this.endpoint = endpoint;
        this.username = username;
        this.password = password;
        this.config = config;
        this.httpClient = HttpClient.newHttpClient();
    }

    public void sendStatement(XapiStatement statement) {
        try {
            String json = statement.toJson();  // you must implement this

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint + "/statements"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", getBasicAuthHeader())
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new XapiClientException("Failed to send XAPI statement", e);
        }
    }

    private String getBasicAuthHeader() {
        String raw = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(raw.getBytes());
    }
}



