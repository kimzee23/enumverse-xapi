package validation;

import org.enums.model.*;
import org.enums.validation.XapiValidator;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InteractionValidationTest {

    private final XapiValidator validator = new XapiValidator();

    private XapiStatement baseStatement(Activity activity) {
        return new XapiStatement(
                null,
                new Actor("mailto:test@test.com", "Tester"),
                new Verb("https://example.com/verb", "Tested"),
                activity,
                Instant.now()
        );
    }

    @Test
    void testMissingInteractionType() {
        InteractionDefinition def = new InteractionDefinition();
        Activity act = new Activity();
        act.setId("q1");
        act.setInteractionDefinition(def);

        XapiValidator.ValidationResult result = validator.validate(baseStatement(act));
        assertFalse(result.isValid());
        assertTrue(result.getMessages().contains("interaction.interactionType is required"));
    }

    @Test
    void testChoiceInteractionMissingIds() {
        InteractionComponent comp = new InteractionComponent(null, null);

        InteractionDefinition def = new InteractionDefinition();
        def.setInteractionType(InteractionType.choice);
        def.setChoices(List.of(comp));

        Activity act = new Activity();
        act.setId("q1");
        act.setInteractionDefinition(def);

        XapiValidator.ValidationResult result = validator.validate(baseStatement(act));

        assertFalse(result.isValid());
        assertTrue(result.getMessages().contains("interaction.choices[0].id is required"));
    }

    @Test
    void testValidChoiceInteraction() {
        InteractionComponent a = new InteractionComponent("a", null);

        InteractionDefinition def = new InteractionDefinition();
        def.setInteractionType(InteractionType.choice);
        def.setChoices(List.of(a));

        Activity act = new Activity();
        act.setId("q1");
        act.setInteractionDefinition(def);

        XapiValidator.ValidationResult result = validator.validate(baseStatement(act));

        assertTrue(result.isValid());
    }
}
