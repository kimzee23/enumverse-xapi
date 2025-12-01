package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Getter
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

    // raw JSON preserved if needed
    @Setter
    @Getter
    private JsonNode raw;

    private final Map<String, Object> other = new HashMap<>();

    public XapiStatement() {}

    public XapiStatement(String id, Actor actor, Verb verb, Activity object, Instant timestamp) {
        this.id = id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
        this.actor = actor;
        this.verb = verb;
        this.object = object;
        this.timestamp = timestamp;
    }


    public XapiStatement(UUID id, Actor actor, Verb verb, Activity object, Result result, Context context, Instant timestamp) {
        this.id = id != null ? id : UUID.randomUUID();
        this.actor = actor;
        this.verb = verb;
        this.object = object;
        this.result = result;
        this.context = context;
        this.timestamp = timestamp;
    }

    @JsonAnySetter
    public void set(String name, Object value) { other.put(name, value); }

    @JsonAnyGetter
    public Map<String, Object> any() { return other; }
}
