package org.enums.xapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Activity {
    @JsonProperty("objectType")
    private String objectType;
    @JsonProperty("id")
    private String id;
    @JsonProperty("definition")
    private Object definition;
}
