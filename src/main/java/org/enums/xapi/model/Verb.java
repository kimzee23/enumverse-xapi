package org.enums.xapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Verb {
    @JsonProperty("id")
    private String id; // IRI
    @JsonProperty("display")
    private Object display;

}
