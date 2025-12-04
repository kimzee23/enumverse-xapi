package org.enums.xapi.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.enums.xapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class XapiValidator {

    private final ObjectMapper mapper = new ObjectMapper();

    public XapiValidator() {}

    @Getter
    public static class ValidationResult {
        private final boolean valid;
        private final List<String> messages;

        public ValidationResult(boolean valid, List<String> messages) {
            this.valid = valid;
            this.messages = messages;
        }
    }

    private void require(Supplier<Boolean> rule, String message, List<String> errors) {
        if (!rule.get()) errors.add(message);
    }

    public ValidationResult validate(JsonNode json) {
        try {
            XapiStatement st = mapper.treeToValue(json, XapiStatement.class);
            return validate(st);
        } catch (Exception e) {
            return new ValidationResult(false,
                    List.of("Invalid JSON mapping: " + e.getMessage()));
        }
    }

    public ValidationResult validate(XapiStatement st) {
        List<String> errors = new ArrayList<>();
        require(() -> st != null, "Statement is null", errors);
        if (st == null) return new ValidationResult(false, errors);

        validateActor(st.getActor(), errors);
        validateAccount(st.getAccount(), errors);
        validateVerb(st.getVerb(), errors);
        validateObject(st.getObject(), errors);
        validateResult(st.getResult(), errors);
        validateAttachments(st.getAttachments(), errors);

        // *** FIXED: context validation now triggered ***
        validateContext(st.getContext(), errors);

        return new ValidationResult(errors.isEmpty(), errors);
    }

    // Actor
    private void validateActor(Actor actor, List<String> errors) {
        require(() -> actor != null, "Missing actor", errors);
        if (actor == null) return;

        boolean hasMbox = notEmpty(actor.getMbox());
        boolean identified = hasMbox;

        require(() -> identified, "Actor must have at least one identifier: mbox", errors);
    }

    // Account
    private void validateAccount(Account account, List<String> errors) {
        if (account == null) return;

        if (!notEmpty(account.getName()))
            errors.add("Account must have a name");

        if (!notEmpty(account.getHomePage()))
            errors.add("Account must have a homePage");
    }
    // Verb
    private void validateVerb(Verb verb, List<String> errors) {
        require(() -> verb != null && notEmpty(verb.getId()), "Missing verb.id", errors);
        if (verb == null) return;

        require(() -> looksLikeIri(verb.getId()), "verb.id is not a valid IRI: " + verb.getId(), errors);
        require(() -> verb.getDisplay() != null && !verb.getDisplay().isEmpty(),
                "verb.display must include at least one language entry", errors);
    }

    // Object / Activity
    private void validateObject(Activity obj, List<String> errors) {
        require(() -> obj != null, "Missing object", errors);
        if (obj == null) return;

        require(() -> notEmpty(obj.getId()), "Missing object.id", errors);

        if (obj.getDefinition() != null) {
            LanguageMap name = obj.getDefinition().getName();
            require(() -> name == null || !name.isEmpty(),
                    "object.definition.name must not be empty when present", errors);
        }
    }

    // Result
    private void validateResult(Result result, List<String> errors) {
        if (result == null) return;

        Score score = result.getScore();
        if (score != null) {
            require(() -> score.getScaled() == null || (score.getScaled() >= -1.0 && score.getScaled() <= 1.0),
                    "result.score.scaled must be between -1.0 and 1.0", errors);

            require(() -> score.getMin() == null || score.getMax() == null || score.getMin() <= score.getMax(),
                    "result.score.min cannot be greater than result.score.max", errors);
        }
    }

    // Attachments

    private void validateAttachments(List<Attachment> list, List<String> errors) {
        if (list == null) return;

        for (int count = 0; count < list.size(); count++) {
            Attachment att = list.get(count);
            String prefix = "attachment[" + count + "] ";

            require(() -> notEmpty(att.getUsageType()), prefix + "missing usageType", errors);
            require(() -> att.getDisplay() != null && !att.getDisplay().isEmpty(),
                    prefix + "missing display (LanguageMap)", errors);
            require(() -> notEmpty(att.getContentType()), prefix + "missing contentType", errors);
            require(() -> att.getLength() != null && att.getLength() >= 0, prefix + "invalid length", errors);
            require(() -> notEmpty(att.getSha2()) || notEmpty(att.getFileUrl()),
                    prefix + "must have either sha2 or fileUrl", errors);
        }
    }

    // Context

    private void validateContext(Context context, List<String> errors) {
        if (context == null) return;

        if (context.getInstructor() != null)
            validateActor(context.getInstructor(), errors);

        if (context.getTeam() != null)
            validateActor(context.getTeam(), errors);

        if (context.getContextActivities() != null)
            validateContextActivities(context.getContextActivities(), errors);

        if (context.getRevision() != null && context.getRevision().isBlank())
            errors.add("context.revision cannot be blank");

        if (context.getPlatform() != null && context.getPlatform().isBlank())
            errors.add("context.platform cannot be blank");

        if (context.getLanguage() != null && !context.getLanguage().isBlank()) {
            if (!context.getLanguage().matches("^[a-zA-Z]{2,8}(-[a-zA-Z]{2,8})?$")) {
                errors.add("context.language must be a valid language tag");
            }
        }

        if (context.getExtensions() != null) {
            for (String key : context.getExtensions().keySet()) {
                if (!looksLikeIri(key)) {
                    errors.add("context.extensions key is not valid IRI: " + key);
                }
            }
        }
    }
    // ContextActivities

    private void validateContextActivities(ContextActivities contextActivities, List<String> errors) {
        if (contextActivities == null) return;

        validateActivityList(contextActivities.getParent(), "contextActivities.parent", errors);
        validateActivityList(contextActivities.getGrouping(), "contextActivities.grouping", errors);
        validateActivityList(contextActivities.getCategory(), "contextActivities.category", errors);
        validateActivityList(contextActivities.getOther(), "contextActivities.other", errors);
    }

    // Activity List Helper

    private void validateActivityList(List<Activity> list, String field, List<String> errors) {
        if (list == null) return;

        for (int i = 0; i < list.size(); i++) {
            Activity a = list.get(i);

            if (a == null) {
                errors.add(field + "[" + i + "] cannot be null");
                continue;
            }

            if (!notEmpty(a.getId())) {
                errors.add(field + "[" + i + "].id is missing");
            }

            if (a.getDefinition() != null) {
                LanguageMap name = a.getDefinition().getName();
                if (name != null && name.isEmpty())
                    errors.add(field + "[" + i + "].definition.name cannot be empty");
            }
        }
    }

    // Helpers

    private boolean notEmpty(String string) {
        return string != null && !string.isBlank();
    }

    private boolean looksLikeIri(String string) {
        return string != null && (string.startsWith("http://") || string.startsWith("https://") || string.contains(":"));
    }

    public void validateOrThrow(XapiStatement st) {
        ValidationResult validationResult = validate(st);
        if (!validationResult.isValid())
            throw new IllegalArgumentException(String.join("; ", validationResult.getMessages()));
    }
}
