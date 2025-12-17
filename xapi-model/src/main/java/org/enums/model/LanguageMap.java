package org.enums.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class LanguageMap extends HashMap<String, Object> {

    public LanguageMap() {
        super();
    }

    @JsonCreator
    public LanguageMap(Map<String, String> maps) {
        super(maps);
    }

    /**
     * Return first value (useful when you don't care about language).
     * @JsonIgnore is implied here because Jackson serializes Maps by entries, not getters.
     */
    public String getFirstValue() {
        if (this.isEmpty()) return null;
        return (String) this.values().iterator().next();
    }

    public static LanguageMap of(String lang, String value) {
        LanguageMap lm = new LanguageMap();
        lm.put(lang, value);
        return lm;
    }
}