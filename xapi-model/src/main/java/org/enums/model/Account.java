package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Account {
    @JsonProperty("homePage")
    private String homePage; // e.g., LRS URL

    @JsonProperty("name")
    private String name;


    public Account(String homePage, String name) {
        this.homePage = homePage;
        this.name = name;
    }
}
