package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account {
    @JsonProperty("homePage")
    private String homePage; // e.g., LRS URL

    @JsonProperty("name")
    private String name;

    public Account() {}

    public Account(String homePage, String name) {
        this.homePage = homePage;
        this.name = name;
    }
}
