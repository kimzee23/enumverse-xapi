package testing;

import org.enums.xapi.model.*;
import org.enums.xapi.validation.XapiValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;

class XapiModelTest {

    @Test
    void testCreateValidXapiStatement() {

            Actor actor = new Actor("mailto:ade.ope@example.com", "ade ope");
        // create verb with language map
        Verb verb = new Verb("http://adlnet.gov/expapi/verbs/answered",
                LanguageMap.of("en-US",
                        "answered"));
        Activity activity = new Activity("https://example.com/course/quiz1",
                "Quiz 1");

        XapiStatement statement = new XapiStatement(UUID.randomUUID().toString(), actor, verb, activity, Instant.now());

        XapiValidator validator = new XapiValidator();

        // validate object (no exception)
        XapiValidator.ValidationResult result = validator.validate(statement);
        assertTrue(result.isValid(), "statement must be valid: " + result.getMessages());

        // check default display
        assertEquals("answered", statement.getVerb().getDefaultDisplay());
        assertEquals("ade ope", statement.getActor().getName());

        // also check activity name fallback
        assertEquals("Quiz 1", statement.getObject().getName());
    }
}

