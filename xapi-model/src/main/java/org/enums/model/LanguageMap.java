package org.enums.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class LanguageMap extends HashMap<String, Object> {


    @JsonCreator
    public LanguageMap(Map<String, String> maps) {
        super(maps);
    }

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