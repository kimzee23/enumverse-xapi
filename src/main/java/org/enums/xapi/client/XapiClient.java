package org.enums.xapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.enums.xapi.model.XapiStatement;
import org.enums.xapi.validation.XapiValidator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class XapiClient {

    private final XapiClientConfig config;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient http;

    public XapiClient(XapiClientConfig config) {
        this.config = config;
        this.http = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(config.getTimeoutSeconds()))
                .build();
    }

    /** ---------------------- SEND SINGLE STATEMENT ---------------------- **/
    public XapiResponse sendStatement(XapiStatement statement) throws Exception {

        List<String> errors = XapiValidator.validate(statement);
        if (!errors.isEmpty()) {
            return new XapiResponse(false, 400, String.join("\n", errors));
        }

        String json = mapper.writeValueAsString(statement);
        HttpRequest request = buildPost(json);

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        return new XapiResponse(
                response.statusCode() >= 200 && response.statusCode() < 300,
                response.statusCode(),
                response.body()
        );
    }

    /** ---------------------- SEND MULTIPLE STATEMENTS ---------------------- **/
    public XapiResponse sendStatements(List<XapiStatement> statements) throws Exception {

        for (XapiStatement s : statements) {
            List<String> errors = XapiValidator.validate(s);
            if (!errors.isEmpty()) {
                return new XapiResponse(false, 400,
                        "Batch validation failed:\n" + String.join("\n", errors));
            }
        }

        String json = mapper.writeValueAsString(statements);
        HttpRequest request = buildPost(json);

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        return new XapiResponse(
                response.statusCode() >= 200 && response.statusCode() < 300,
                response.statusCode(),
                response.body()
        );
    }

    /** ---------------------- INTERNAL BUILDER ---------------------- **/
    private HttpRequest buildPost(String jsonBody) {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(config.getEndpoint()))
                .header("Content-Type", "application/json")
                .header("X-Experience-API-Version", "1.0.3")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));

        String auth = config.getBasicAuthHeader();
        if (auth != null) builder.header("Authorization", auth);

        return builder.build();
    }
}
