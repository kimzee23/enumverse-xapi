package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
public class XapiStatement {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("actor")
    private Actor actor;

    @JsonProperty("account")
    private Account account;

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

    @JsonProperty("attachments")
    private List<Attachment> attachments = new ArrayList<>();

    @Setter
    private JsonNode raw;

    private final Map<String, Object> other = new HashMap<>();

    public XapiStatement() {}

    public XapiStatement(String id, Actor actor, Verb verb,
                         Activity object, Instant timestamp) {
        this.id = (id != null && !id.isBlank()) ?
                UUID.fromString(id) : UUID.randomUUID();
        this.actor = actor;
        this.verb = verb;
        this.object = object;
        this.timestamp = timestamp;
    }

    public XapiStatement(String string, Actor actor, Verb did, Activity activity, Instant now, Context ctx) {
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    @JsonAnySetter
    public void set(String name, Object value) { other.put(name, value); }

    @JsonAnyGetter
    public Map<String, Object> any() { return other; }
}
