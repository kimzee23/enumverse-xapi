package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public class LanguageMap extends HashMap<String, Object> {
    public LanguageMap() {
        super();
    }

    @JsonCreator
    public LanguageMap(java.util.Map<String, String> maps) {
        super(maps);
    }
    @JsonValue
    public java.util.Map<String, Object> toMap() {
        return this;
    }

    /**
     * Return first value (useful when you don't care about language).
     */
    public String getFirstValue() {
        if (this.isEmpty()) return null;
        return (String) this.values().iterator().next();
    }

    /**
     * Convenient constructor for single-language entries.
     */
    public static LanguageMap of(String lang, String value) {
        LanguageMap lm = new LanguageMap();
        lm.put(lang, value);
        return lm;
    }
}
