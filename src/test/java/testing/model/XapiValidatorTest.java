package testing.model;

import org.enums.xapi.model.Activity;
import org.enums.xapi.model.Actor;
import org.enums.xapi.model.Verb;
import org.enums.xapi.model.XapiStatement;
import org.enums.xapi.validation.XapiValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XapiValidatorTest {

    private final XapiValidator validator = new XapiValidator();

    @DisplayName("Valid statement")
    @Test
    void testValidStatement() {
        Actor actor = new Actor("mailto:ade@mailinator.com", "Ade");
        Verb verb = new Verb("https://example.com/verbs/completed", "Completed");
        Activity activity = new Activity("act1", "Watched video");

        XapiStatement st = new XapiStatement(null, actor, verb, activity, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(st);
        assertTrue(result.isValid());
    }
    @DisplayName("Missing Account")
    @Test
    void testMissingActor() {
        Verb verb = new Verb("x", "Test");
        Activity act = new Activity("122101", "Action");

        XapiStatement xapiStatement = new XapiStatement(null, null, verb, act, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(xapiStatement);
        assertFalse(result.isValid());
        assertTrue(result.getMessages().contains("Missing actor"));
    }

    @Test
    void testMissingVerb() {
        Actor actor = new Actor("mailto:test@mailinatorcom", "Action");
        Activity act = new Activity("a", "Action");

        XapiStatement valid = new XapiStatement(null, actor, null, act, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(valid);
        assertFalse(result.isValid());
    }

    @Test
    void testMissingObjectId() {
        Actor actor = new Actor("mailto:test@mailinator.com", "Action");
        Verb verb = new Verb("x", "Test");

        Activity act = new Activity();
        act.setId(null);

        XapiStatement st = new XapiStatement(null, actor, verb, act, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(st);
        assertFalse(result.isValid());
    }
}