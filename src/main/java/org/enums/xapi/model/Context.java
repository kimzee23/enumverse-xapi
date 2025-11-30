package org.enums.xapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Context {
    @JsonProperty("registration")
    private String registration;
    @JsonProperty("contextActivities")
    private Object contextActivities;
    @JsonProperty("instructor")
    private Actor instructor;
}
