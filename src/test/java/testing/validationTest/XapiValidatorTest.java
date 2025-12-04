package testing.validationTest;

import org.enums.xapi.model.*;
import org.enums.xapi.validation.XapiValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XapiValidatorTest {

    private final XapiValidator validator = new XapiValidator();

    @DisplayName("Valid full statement")
    @Test
    void testValidFullStatement() {
        Actor actor = new Actor("mailto:ade@mailinator.com", "Ade");
        Account account = new Account("https://example.com", "user123");
        actor.setMbox("mailto:ade@mailinator.com"); // actor has identifier

        Verb verb = new Verb("https://example.com/verbs/completed", "Completed");
        Activity activity = new Activity("act1", "Watched video");

        XapiStatement st = new XapiStatement(null, actor, verb, activity, Instant.now());
        st.setAccount(account);

        Context context = new Context();
        context.setInstructor(actor);
        context.setRevision("rev-1");
        context.setPlatform("Web");
        context.setLanguage("en-US");
        st.setContext(context);

        XapiValidator.ValidationResult result = validator.validate(st);
        assertTrue(result.isValid(), "Full valid statement should pass validation");
    }

    @DisplayName("Missing actor")
    @Test
    void testMissingActor() {
        Verb verb = new Verb("https://example.com/verbs/attempted", "Attempted");
        Activity act = new Activity("122101", "Action");

        XapiStatement xapiStatement = new XapiStatement(null, null, verb, act, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(xapiStatement);
        assertFalse(result.isValid());
        assertTrue(result.getMessages().contains("Missing actor"));
    }

    @DisplayName("Actor missing identifier")
    @Test
    void testActorWithoutMbox() {
        Actor actor = new Actor();
        Verb verb = new Verb("https://example.com/verbs/tested", "Tested");
        Activity act = new Activity("a1", "Test Activity");

        XapiStatement st = new XapiStatement(null, actor, verb, act, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(st);
        assertFalse(result.isValid());
        assertTrue(result.getMessages().contains("Actor must have at least one identifier: mbox"));
    }

    @DisplayName("Missing verb")
    @Test
    void testMissingVerb() {
        Actor actor = new Actor("mailto:test@mailinator.com", "Action");
        Activity act = new Activity("a", "Action");

        XapiStatement st = new XapiStatement(null, actor, null, act, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(st);
        assertFalse(result.isValid());
        assertTrue(result.getMessages().contains("Missing verb.id"));
    }

    @DisplayName("Invalid verb IRI")
    @Test
    void testInvalidVerbIri() {
        Actor actor = new Actor("mailto:test@mailinator.com", "Action");
        Verb verb = new Verb("invalidVerb", "Test");
        Activity act = new Activity("act1", "Test Activity");

        XapiStatement st = new XapiStatement(null, actor, verb, act, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(st);
        assertFalse(result.isValid());
        assertTrue(result.getMessages().stream().anyMatch(m -> m.contains("verb.id is not a valid IRI")));
    }

    @DisplayName("Missing object ID")
    @Test
    void testMissingObjectId() {
        Actor actor = new Actor("mailto:test@mailinator.com", "Action");
        Verb verb = new Verb("https://example.com/verbs/tested", "Test");

        Activity act = new Activity();
        act.setId(null);

        XapiStatement st = new XapiStatement(null, actor, verb, act, Instant.now());

        XapiValidator.ValidationResult result = validator.validate(st);
        assertFalse(result.isValid());
        assertTrue(result.getMessages().contains("Missing object.id"));
    }

    @DisplayName("Context validation - missing instructor ID")
    @Test
    void testContextInvalidInstructor() {
        Actor instructor = new Actor();
        Context context = new Context();
        context.setInstructor(instructor);

        Actor actor = new Actor("mailto:test@mailinator.com", "Actor");
        Verb verb = new Verb("https://example.com/verbs/tested", "Test");
        Activity act = new Activity("act1", "Test Activity");

        XapiStatement st = new XapiStatement(null, actor, verb, act, Instant.now());
        st.setContext(context);

        XapiValidator.ValidationResult result = validator.validate(st);
        assertFalse(result.isValid());
        assertTrue(result.getMessages().contains("Actor must have at least one identifier: mbox"));
    }

    @DisplayName("ContextActivities validation - missing activity ID")
    @Test
    void testContextActivitiesInvalid() {
        Activity parent = new Activity();
        ContextActivities ca = new ContextActivities();
        ca.setParent(List.of(parent));

        Context context = new Context();
        context.setContextActivities(ca);

        Actor actor = new Actor("mailto:test@mailinator.com", "Actor");
        Verb verb = new Verb("https://example.com/verbs/tested", "Test");
        Activity act = new Activity("act1", "Test Activity");

        XapiStatement st = new XapiStatement(null, actor, verb, act, Instant.now());
        st.setContext(context);

        XapiValidator.ValidationResult result = validator.validate(st);
        assertFalse(result.isValid());
        assertTrue(result.getMessages().stream().anyMatch(m -> m.contains("contextActivities.parent[0].id is missing")));
    }
}
