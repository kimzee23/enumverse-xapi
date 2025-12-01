package org.enums.xapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Activity {

    @JsonProperty("objectType")
    private String objectType = "Activity";

    @JsonProperty("id")
    private String id;

    @JsonProperty("definition")
    private Definition definition;

    public Activity() {}

    public Activity(String id, String name) {
        this.id = id;
        this.definition = new Definition(LanguageMap.of("en-US", name));
    }

    /**
     * Convenience to get the human-readable name (first-language fallback).
     */
    public String getName() {
        if (definition == null || definition.getName() == null) return null;
        return definition.getName().getFirstValue();
    }

    @Setter
    @Getter
    public static class Definition {
        @JsonProperty("name")
        private LanguageMap name;

        public Definition() {}

        public Definition(LanguageMap name) {
            this.name = name;
        }

    }
}
