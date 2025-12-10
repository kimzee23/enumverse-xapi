package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("account")
    private Account account;

    public Actor() {}

    public Actor(String mbox, String name) {
        this.objectType = "Agent";
        this.mbox = mbox;
        this.name = name;
    }

}
