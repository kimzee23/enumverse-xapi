package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Actor {
    @JsonProperty("objectType")
    private String objectType; // Agent / Group
    @JsonProperty("mbox")
    private String mbox;

    @JsonProperty("name")
    private String name;


    public Actor() {}

    public Actor(String mbox, String name) {
        this.objectType = "Agent";
        this.mbox = mbox;
        this.name = name;
    }

}
