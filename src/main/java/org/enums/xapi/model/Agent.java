package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Agent {
    @JsonProperty("objectType")
    private String objectType = "Agent"; //always agent

    @JsonProperty("name")
    private String name;
    @JsonProperty("mbox")
    private String mbox;
    @JsonProperty("account")
    private Actor.Account account;


}
