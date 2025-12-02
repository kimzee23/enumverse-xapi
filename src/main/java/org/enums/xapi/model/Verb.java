package org.enums.xapi.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Verb {

    @JsonProperty("id")
    private final String id; // IRI

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

    /**
     * Convenience: returns the display value for the requested language,
     * or the first available entry if language not present.
     */
    public String getDisplay(String lang) {
        if (display == null) return null;
        String verb = (String) display.get(lang);
        return verb != null ? verb : display.getFirstValue();
    }

    /**
     * Convenience: get a default display string (first entry).
     */
    public String getDefaultDisplay() {
        return display == null ? null : display.getFirstValue();
    }

}
