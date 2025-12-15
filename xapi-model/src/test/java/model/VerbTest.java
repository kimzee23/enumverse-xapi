package model;

import org.enums.model.LanguageMap;
import org.enums.model.Verb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VerbTest {

    @DisplayName("Verb should store id and language map correctly")
    @Test
    void testVerbWithLanguageMap() {
        LanguageMap map = LanguageMap.of("en-US", "answered");
        Verb verb = new Verb("https://adlnet.gov/expapi/verbs/answered", map);

        assertEquals("https://adlnet.gov/expapi/verbs/answered", verb.getId());
        assertEquals("answered", verb.getDefaultDisplay());
    }

    @DisplayName("Verb convenience constructor should create LanguageMap automatically")
    @Test
    void testVerbWithStringDisplay() {
        Verb verb = new Verb("https://xapi.com/verbs/viewed", "Viewed");

        assertEquals("https://xapi.com/verbs/viewed", verb.getId());
        assertEquals("Viewed", verb.getDefaultDisplay());
    }

    @DisplayName("Verb getDisplay(lang) should return correct language")
    @Test
    void testGetDisplayForSpecificLanguage() {
        LanguageMap map = LanguageMap.of("en-US", "answered");
        map.put("fr-FR", "répondu");

        Verb verb = new Verb("https://example.com/answered", map);

        assertEquals("répondu", verb.getDisplay("fr-FR"));
        assertEquals("answered", verb.getDisplay("en-US"));
    }

    @DisplayName("Verb getDisplay(lang) should fallback to first available")
    @Test
    void testGetDisplayFallback() {
        LanguageMap map = LanguageMap.of("en-GB", "completed");
        Verb verb = new Verb("https://xapi.com/verbs/completed", map);

        assertEquals("completed", verb.getDisplay("unknown-lang"));
    }

    @DisplayName("Verb should return the first available display value.")
    @Test
    void testGetDefaultDisplay() {
        LanguageMap map = new LanguageMap();
        map.put("es-ES", "respondido");

        Verb verb = new Verb("id", map);

        assertEquals("respondido", verb.getDefaultDisplay());
    }
}
