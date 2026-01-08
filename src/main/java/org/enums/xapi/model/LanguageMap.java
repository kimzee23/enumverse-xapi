package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
     */
    @JsonIgnore
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
