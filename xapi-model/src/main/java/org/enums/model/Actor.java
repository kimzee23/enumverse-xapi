package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Actor {
    @JsonProperty("objectType")
    private String objectType;
    @JsonProperty("mbox")
    private String mbox;

    @JsonProperty("name")
    private String name;

    @JsonProperty("account")
    private Account account;

    public Actor(String mbox, String name) {
        this.objectType = "Agent";
        this.mbox = mbox;
        this.name = name;
    }

}
