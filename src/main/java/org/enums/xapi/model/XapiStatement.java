package org.enums.xapi.model;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
public class XapiStatement {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("actor")
    private Actor actor;
    @JsonProperty("verb")
    private Verb verb;
    @JsonProperty("object")
    private Activity object;
    @JsonProperty("result")
    private Result result;
    @JsonProperty("context")
    private Context context;
    @JsonProperty("timestamp")
    private Instant timestamp;
    private JsonNode raw;
    private final Map<String,Object> other = new HashMap<>();

    public XapiStatement() {}

    public XapiStatement(String string, Actor actor, Verb verb, Activity activity, Instant now) {
    }

    // getters / setters (omitted here for brevity — include standard getters and setters)
    // helper for timestamp string
    @JsonProperty("timestamp")
    public void setTimestamp(String ts) {
        if (ts == null || ts.isBlank()) this.timestamp = null;
        else this.timestamp = Instant.parse(ts);
    }

    @JsonAnySetter
    public void setOther(String name, Object value) { other.put(name, value); }

    @JsonAnyGetter
    public Map<String,Object> any() { return other; }


}
