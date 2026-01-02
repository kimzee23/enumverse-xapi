package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Verb {

    @JsonProperty("id")
    private String id;

    @JsonProperty("display")
    private  LanguageMap display;

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
