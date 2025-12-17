package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
public class Context {

    @JsonProperty("registration")
    private UUID registration;

    @JsonProperty("instructor")
    private Actor instructor;

    @JsonProperty("team")
    private Actor team;

    @JsonProperty("contextActivities")
    private ContextActivities contextActivities;

    @JsonProperty("revision")
    private String revision;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("language")
    private String language;

    @JsonProperty("extensions")
    private Map<String, Object> extensions = new HashMap<>();


}
