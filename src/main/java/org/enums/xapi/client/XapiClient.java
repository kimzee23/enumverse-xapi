package org.enums.xapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.enums.xapi.model.XapiStatement;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class XapiClient {

    private final String endpoint;
    private final ObjectMapper mapper = new ObjectMapper();

    public XapiClient(String endpoint) {
        this.endpoint = endpoint;
    }

    public XapiResponse sendStatement(XapiStatement statement) {
        // TODO: implement HTTP POST to LRS
        return new XapiResponse(true, "Statement sent");
    }

    public XapiResponse sendStatements(List<XapiStatement> statements) {
        // TODO: implement batch send
        return new XapiResponse(true, "Batch sent");
    }

}
