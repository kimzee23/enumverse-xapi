package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class Agent {
    @JsonProperty("objectType")
    private String objectType = "Agent";

    @JsonProperty("name")
    private String name;

    @JsonProperty("mbox")
    private String mbox;

    @JsonProperty("account")
    private Account account;

    @JsonProperty("member")
    private List<Agent> member;

    @JsonProperty("openid")
    private String openid;

    public Agent() {}

    public Agent(String mbox, String name) {
        this.mbox = mbox;
        this.name = name;
    }
}
