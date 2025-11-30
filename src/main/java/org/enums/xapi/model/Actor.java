package org.enums.xapi.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Actor {
    @JsonProperty("objectType")
    private String objectType; // Agent / Group

    @JsonProperty("mbox")
    private String mbox;

    @JsonProperty("name")
    private String name;

    @JsonProperty("account")
    private Account account;

    public static class Account {
        @JsonProperty("homePage")
        private String homePage;
        @JsonProperty("name")
        private String name;
        // getters/setters
    }
    // getters/setters
}
