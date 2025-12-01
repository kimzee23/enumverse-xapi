package testing;

import org.enums.xapi.model.Activity;
import org.enums.xapi.model.Actor;
import org.enums.xapi.model.Verb;
import org.enums.xapi.model.XapiStatement;
import org.enums.xapi.validation.XapiValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.Instant;
import java.util.UUID;

class XapiModelTest {

    @Test
    void testCreateValidXapiStatement() {

        // --- Build Actor ---
        Actor actor = new Actor(
                "mailto:ade.ope@example.com",
                "ade ope"
        );

        // --- Build Verb ---
        Verb verb = new Verb(
                "http://adlnet.gov/expapi/verbs/answered",
                "answered"
        );

        // --- Build Activity ---
        Activity activity = new Activity(
                "https://example.com/course/quiz1",
                "Quiz 1"
        );

        // --- Build Statement ---
        XapiStatement statement = new XapiStatement(
                UUID.randomUUID().toString(),
                actor,
                verb,
                activity,
                Instant.now()
        );

        // --- Validate ---
        XapiValidator validator = new XapiValidator();

        //  Assert that no exception is thrown
        assertDoesNotThrow(() -> validator.validate(statement));

        // Verify data
        assertEquals("answered", statement.getVerb().getDisplay());
        assertEquals("ade ope", statement.getActor().getName());
    }
}
