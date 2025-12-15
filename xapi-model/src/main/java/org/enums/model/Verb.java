package org.enums.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Verb {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("display")
    private final LanguageMap display;

    @JsonCreator
    public Verb(@JsonProperty("id") String id,
                @JsonProperty("display") LanguageMap display) {
        this.id = id;
        this.display = display;
    }

    public Verb(String id, String displayString) {
        this(id, LanguageMap.of("en-US", displayString));
    }

    public String getDisplay(String lang) {
        if (display == null) return null;
        String verb = (String) display.get(lang);
        return verb != null ? verb : display.getFirstValue();
    }

    public String getDefaultDisplay() {
        if (display == null) return null;
        return display.getFirstValue();
    }
}
