package testing.model;

import org.enums.xapi.model.LanguageMap;
import org.enums.xapi.model.Verb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerbTest {
    @DisplayName("constructor testing")
    @Test
    public void testVerbConstruct() {
        Verb verb = new Verb("https://example.com/verb/completed", "completed");
        assertEquals("https://example.com/verb/completed", verb.getId());
        assertEquals("completed",verb.getDefaultDisplay());
    }
    @DisplayName("Language fallback test")
    @Test
    public void testLanguageFallback() {
        LanguageMap languageMap = LanguageMap.of("en-us","completed");
        Verb verb = new Verb("X", languageMap);
        assertEquals("completed", verb.getDisplay("en-us"));
        assertEquals("completed", verb.getDisplay("fr-fr"));
    }
    @DisplayName("InvalidIRI")
    @Test
    public void testInvalidIRI() {
        Verb verb = new Verb("this_not_a_url", LanguageMap.of("en-us","Done"));
        assertEquals("this_not_a_url", verb.getId());
    }
}

