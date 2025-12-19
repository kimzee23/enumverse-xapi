package org.enums.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.enums.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


@NoArgsConstructor
public class XapiValidator {

    private final ObjectMapper mapper = new ObjectMapper();



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


        validateContext(st.getContext(), errors);

        return new ValidationResult(errors.isEmpty(), errors);
    }

    // Actor
    private void validateActor(Actor actor, List<String> errors) {
        require(() -> actor != null, "Missing actor", errors);
        if (actor == null) return;

        boolean hasMbox = notEmpty(actor.getMbox());
        boolean hasAccount = actor.getAccount() != null;

        boolean identified = hasMbox || hasAccount;

        require(() -> identified,
                "Actor must have at least one identifier: mbox or account",
                errors);

        if (hasAccount) {
            validateAccount(actor.getAccount(), errors);
        }
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
        if (obj.getDefinition() != null &&
                obj.getDefinition().getInteraction() != null) {

            validateInteractionDefinition(
                    obj.getDefinition().getInteraction(),
                    errors
            );
        }

    }


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


    private boolean isLanguageTag(String lang) {
        return lang.matches("^[a-zA-Z]{2,8}(-[a-zA-Z0-9]{2,8})*$");
    }


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

        if (context.getLanguage() != null) {
            String lang = context.getLanguage();
            if (!lang.isBlank() && !isLanguageTag(lang)) {
                errors.add("context.language must be a valid BCP-47 language tag");
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

    private void validateContextActivities(ContextActivities contextActivities, List<String> errors) {
        if (contextActivities == null) return;

        validateActivityList(contextActivities.getParent(), "contextActivities.parent", errors);
        validateActivityList(contextActivities.getGrouping(), "contextActivities.grouping", errors);
        validateActivityList(contextActivities.getCategory(), "contextActivities.category", errors);
        validateActivityList(contextActivities.getOther(), "contextActivities.other", errors);
    }


    private void validateActivityList(List<Activity> list, String field, List<String> errors) {
        if (list == null) return;

        for (int count = 0; count < list.size(); count++) {
            Activity a = list.get(count);

            if (a == null) {
                errors.add(field + "[" + count + "] cannot be null");
                continue;
            }

            if (!notEmpty(a.getId())) {
                errors.add(field + "[" + count + "].id is missing");
            }

            if (a.getDefinition() != null) {
                LanguageMap name = a.getDefinition().getName();
                if (name != null && name.isEmpty())
                    errors.add(field + "[" + count + "].definition.name cannot be empty");
            }
        }
    }

    private void validateInteractionDefinition(InteractionDefinition interactionDefinition, List<String> errors) {
        if (interactionDefinition == null) return;

        if (interactionDefinition.getInteractionType() == null) {
            errors.add("interaction.interactionType is required");
            return;
        }

        switch (interactionDefinition.getInteractionType()) {
            case choice:
            case sequencing:
                validateInteractionList(interactionDefinition.getChoices(), "interaction.choices", errors);
                break;

            case likert:
                validateInteractionList(interactionDefinition.getScale(), "interaction.scale", errors);
                break;

            case matching:
                validateInteractionList(interactionDefinition.getSource(), "interaction.source", errors);
                validateInteractionList(interactionDefinition.getTarget(), "interaction.target", errors);
                break;

            case performance:
                validateInteractionList(interactionDefinition.getSteps(), "interaction.steps", errors);
                break;

            case numeric:
            case fillIn:
            case longFillIn:
            case other:
                break;
        }
    }


    private void validateInteractionList(List<InteractionComponent> list, String field, List<String> errors) {
        if (list == null) return;

        for (int count = 0; count < list.size(); count++) {
            InteractionComponent comp = list.get(count);

            if (comp == null) {
                errors.add(field + "[" + count + "] cannot be null");
                continue;
            }

            if (comp.getId() == null || comp.getId().isBlank()) {
                errors.add(field + "[" + count + "].id is required");
            }
        }
    }

    // Helpers

    private boolean notEmpty(String string) {
        return string != null && !string.isBlank();
    }

    private boolean looksLikeIri(String s) {
        return s != null &&
                (s.startsWith("http://") || s.startsWith("https://"));
    }


    public void validateOrThrow(XapiStatement st) {
        ValidationResult validationResult = validate(st);
        if (!validationResult.isValid())
            throw new IllegalArgumentException(String.join("; ", validationResult.getMessages()));
    }
}
