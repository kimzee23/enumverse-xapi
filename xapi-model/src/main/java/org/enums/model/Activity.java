package org.enums.model;

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

    public String getName() {
        if (definition == null || definition.getName() == null) return null;
        return definition.getName().getFirstValue();
    }

    public void setInteractionDefinition(InteractionDefinition interaction) {
        if (definition == null) definition = new Definition();
        definition.setInteraction(interaction);
    }

    @Getter
    @Setter
    public static class Definition {
        @JsonProperty("name")
        private LanguageMap name;

        @JsonProperty("interaction")
        private InteractionDefinition interaction;

        public Definition() {}

        public Definition(LanguageMap name) {
            this.name = name;
        }
    }
}
